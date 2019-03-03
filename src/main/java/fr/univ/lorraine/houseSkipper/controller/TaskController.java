package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.*;
import fr.univ.lorraine.houseSkipper.repositories.*;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class TaskController {

    private TaskRepository repository;
    private AuthenticatedUserService authenticatedUserService;
    private PartieExacteRepository partieExacteRepository;
    private TypeSecondaireRepository typeSecondaireRepository;
    private PhaseRepository phaseRepository;
    private HistoricRepository historicRepository;


    public TaskController(TaskRepository repository, AuthenticatedUserService authenticatedUserService, PartieExacteRepository partieExacteRepository,
                          TypeSecondaireRepository typeSecondaireRepository, PhaseRepository phaseRepository, HistoricRepository historicRepository){

        this.repository = repository;
        this.authenticatedUserService = authenticatedUserService;
        this.partieExacteRepository = partieExacteRepository;
        this.typeSecondaireRepository = typeSecondaireRepository;
        this.phaseRepository = phaseRepository;
        this.historicRepository = historicRepository;
    }

    @GetMapping("tasks")
    public List<Task> tasksList(){
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            t.setCurrentPhase(t.getStatus().getPhaseName());
            for (Historic h:
                 t.getHistorics()) {
                h.setCurrentSubPhase(h.getSubPhase().getSPhaseName());
                System.out.println(h.getSubPhase().getSPhaseName() + "=========");
            }
        }
        return authenticatedUserService.getAuthenticatedUser().getTasks();
    }

    @PostMapping("tasks/next")
    public Task toNextPhase(@Valid @RequestBody Task task)
    {
        System.out.println(repository.findById(task.getId()).get().getCurrentPhase() + "-)-)-)-)-)");
        Task t = repository.findById(task.getId()).get();
        if(t.getUser().equals(authenticatedUserService.getAuthenticatedUser())) {
            Phase p = phaseRepository.findByPhaseName(t.getCurrentPhase());
            if (phaseRepository.findAll().size() < p.getId()+1){
                Phase pnext =  phaseRepository.findById(p.getId()+1).get();

                t.setStatus(null);
                t.setStatus(pnext);
                t.setCurrentPhase(pnext.getPhaseName());

            }
            repository.saveAndFlush(t);
            System.out.println(repository.findById(task.getId()).get().getCurrentPhase() + "-)-)-)-)-)");
            return repository.findById(task.getId()).get();
        }
        return new Task();

    }

    @GetMapping("tasks/{taskId}")
    public Task taskById(@PathVariable String taskId){
        return repository.findById(Long.parseLong(taskId)).map(task -> {
            if (task.getUser().equals(authenticatedUserService.getAuthenticatedUser())) {
                if(task == null){
                    return new Task();
                }else {
                    task.setCurrentPhase(task.getStatus().getPhaseName());
                    task.getHistorics().get(task.getHistorics().size() - 1).setCurrentSubPhase(task.getHistorics().get(task.getHistorics().size() - 1).getSubPhase().getSPhaseName());
                    return task;
                }
            }return new Task();


        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }



    @PostMapping("tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        List<PartieExacte> partieExactes = task.getPartiesExacte();
        List<TypeSecondaire> typeSecondaires = task.getTypeSecondaires();
        List<Historic> historics = task.getHistorics();
        Phase phase = task.getStatus();
        //task.setStatus(null);
        task.setHistorics(null);
        task.setPartiesExacte(null);
        task.setTypeSecondaires(null);
        System.out.println(task.getStatus().getPhaseName() + "°°°°°°°°°ààà");

        Phase ph = phaseRepository.findByPhaseName(phase.getPhaseName());
        System.out.println(phaseRepository.findAll().size() + "°°°°°°°°°ààà");
        ApplicationUser user = authenticatedUserService.getAuthenticatedUser();
        task.setUser(user);
        task.setStatus(ph);
        //task.setStatus(ph);
        Task t = repository.saveAndFlush(task);


        t.setStatus(ph);
        Task k = repository.saveAndFlush(t);
        for (PartieExacte p:
                partieExactes) {
            p.setTask(task);
            partieExacteRepository.save(p);
        }
        if(!typeSecondaires.isEmpty()) {
            for (TypeSecondaire type :
                    typeSecondaires) {
                type.setTask(task);
                typeSecondaireRepository.save(type);

            }
        }
        Historic h = new Historic();
        h.setDate(new Date());
        h.setTask(task);
        for (SubPhase s:
             ph.getSubPhase()) {
            System.out.println(s.getSPhaseName()+")))))))))))))°°°°");

        }
        h.setSubPhase(ph.getSubPhase().get(1));
        historicRepository.save(h);
        System.out.println(k.getStatus().getPhaseName() + "°°°°°°°°°");
        return task;

    }

    @PutMapping("tasks/{taskId}")
    public Task updateTask(@PathVariable String taskId, @Valid @RequestBody Task taskRequest) {
        return repository.findById(Long.parseLong(taskId)).map(task -> {
            List<PartieExacte> partieExactes = taskRequest.getPartiesExacte();
            List<TypeSecondaire> typeSecondaires = taskRequest.getTypeSecondaires();

            taskRequest.setPartiesExacte(null);
            task.setTypeSecondaires(null);
            taskRequest.setTypeSecondaires(null);
            task.setPartiesExacte(null);
            if (task.getUser().equals(authenticatedUserService.getAuthenticatedUser())) {
                task.setDescription(taskRequest.getDescription());
                task.setPartie(taskRequest.getPartie());
                task.setStatus(taskRequest.getStatus());
                task.setType(taskRequest.getType());
                task.setConnaissance(taskRequest.getConnaissance());
                task.setResultat(taskRequest.getResultat());

                repository.saveAndFlush(task);

                for (PartieExacte pa:
                     partieExacteRepository.findAllByTask(task)) {
                    partieExacteRepository.delete(pa);
                }
                for (PartieExacte p:
                        partieExactes) {
                    p.setTask(task);
                    partieExacteRepository.save(p);
                }
                if(task.getType().equals("")){
                    for (TypeSecondaire ts:
                            typeSecondaireRepository.findAllByTask(task)) {
                        typeSecondaireRepository.delete(ts);
                    }
                    for (TypeSecondaire tss:
                            typeSecondaires) {
                        tss.setTask(task);
                        typeSecondaireRepository.save(tss);
                    }
                } else {
                    for (TypeSecondaire ts:
                            typeSecondaireRepository.findAllByTask(task)) {
                        typeSecondaireRepository.delete(ts);
                    }
                }
                return task;
            }return new Task();

        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }

    @DeleteMapping("tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        return repository.findById(taskId).map(task -> {
            repository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }
}