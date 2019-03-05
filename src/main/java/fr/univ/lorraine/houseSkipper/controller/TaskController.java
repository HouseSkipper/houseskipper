package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.*;
import fr.univ.lorraine.houseSkipper.repositories.*;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TaskController {

    private TaskRepository repository;
    private AuthenticatedUserService authenticatedUserService;
    private PartieExacteRepository partieExacteRepository;
    private TypeSecondaireRepository typeSecondaireRepository;
    private PhaseRepository phaseRepository;
    private HistoricRepository historicRepository;
    private SubPhaseRepository subPhaseRepository;


    public TaskController(TaskRepository repository, AuthenticatedUserService authenticatedUserService, PartieExacteRepository partieExacteRepository,
                          TypeSecondaireRepository typeSecondaireRepository, PhaseRepository phaseRepository, HistoricRepository historicRepository,
                          SubPhaseRepository subPhaseRepository){

        this.repository = repository;
        this.authenticatedUserService = authenticatedUserService;
        this.partieExacteRepository = partieExacteRepository;
        this.typeSecondaireRepository = typeSecondaireRepository;
        this.phaseRepository = phaseRepository;
        this.historicRepository = historicRepository;
        this.subPhaseRepository= subPhaseRepository;
    }

    @GetMapping("tasks")
    public List<Task> tasksList(){
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            t.setCurrentPhase(t.getStatus().getPhaseName());
            for (Historic h:
                 t.getHistorics()) {
                h.setCurrentSubPhase(h.getSubPhase().getSPhaseName());
                h.setCurrentPhase(h.getPhase().getPhaseName());
                System.out.println(h.getSubPhase().getSPhaseName() + "=========");
            }
            for (Commentaire c:
                 t.getCommentaires()) {
                c.setPhasec(c.getPhase().getPhaseName());
            }
        }
        return authenticatedUserService.getAuthenticatedUser().getTasks();
    }

    @PostMapping("tasks/next/{taskId}")
    public Task toNextPhase(@PathVariable String taskId, @Valid @RequestBody Task task)
    {
       Task t = repository.findByNom(task.getNom()).get(0);
        System.out.println(t.getStatus().getPhaseName() + "-)-)-)-)-)");


        if(t.getUser().equals(authenticatedUserService.getAuthenticatedUser())) {
            Phase p = phaseRepository.findByPhaseName(t.getStatus().getPhaseName());
            System.out.println(phaseRepository.findAll().size());
            System.out.println(p.getId());
            if (phaseRepository.findAll().size() > p.getId()){
                Phase pnext =  phaseRepository.findById(new Long(p.getId()+1)).get();

                t.setStatus(null);
                t.setStatus(pnext);
                t.setCurrentPhase(pnext.getPhaseName());
                System.out.println(pnext.getPhaseName());


                repository.saveAndFlush(t);
                Historic h = new Historic();
                h.setDate(LocalDate.now());
                h.setTask(task);
                h.setPhase(pnext);
                h.setSubPhase(pnext.getSubPhase().get(1));
                historicRepository.save(h);

            }

            System.out.println(repository.findById(task.getId()).get().getStatus().getPhaseName() + "-)-)-)-)-)");
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
                    task.setCurrentPhaseId(task.getStatus().getId());
                    task.getHistorics().get(task.getHistorics().size() - 1).setCurrentSubPhase(task.getHistorics().get(task.getHistorics().size() - 1).getSubPhase().getSPhaseName());
                    System.out.println(task.getCurrentPhase() + ")))----" + task.getCurrentPhaseId());
                    for (Commentaire c:
                            task.getCommentaires()) {
                        c.setPhasec(c.getPhase().getPhaseName());
                    }
                    return task;
                }
            }return new Task();


        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }



    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        System.out.println(task);
        List<PartieExacte> partieExactes = task.getPartiesExacte();
        List<TypeSecondaire> typeSecondaires = task.getTypeSecondaires();
        List<Historic> historics = task.getHistorics();
        //task.setStatus(null);
        task.setHistorics(null);
        task.setPartiesExacte(null);
        task.setTypeSecondaires(null);
        task.setCommentaires(null);
        Phase ph = phaseRepository.findByPhaseName("Redaction");
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
        h.setDate(LocalDate.now());
        h.setTask(task);
        h.setPhase(ph);
        for (SubPhase s:
             ph.getSubPhase()) {
            System.out.println(s.getSPhaseName()+")))))))))))))°°°°");

        }
        h.setSubPhase(ph.getSubPhase().get(1));
        historicRepository.save(h);
        System.out.println(k.getStatus().getPhaseName() + "°°°°°°°°°");
        return repository.findById(t.getId()).get();

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
                task.setType(taskRequest.getType());
                task.setConnaissance(taskRequest.getConnaissance());
                task.setResultat(taskRequest.getResultat());
                task.setStatus(task.getStatus());
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