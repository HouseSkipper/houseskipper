package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.PartieExacte;
import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.model.TypeSecondaire;
import fr.univ.lorraine.houseSkipper.repositories.PartieExacteRepository;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import fr.univ.lorraine.houseSkipper.repositories.TypeSecondaireRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskController {

    private TaskRepository repository;
    private AuthenticatedUserService authenticatedUserService;
    private PartieExacteRepository partieExacteRepository;
    private TypeSecondaireRepository typeSecondaireRepository;


    public TaskController(TaskRepository repository, AuthenticatedUserService authenticatedUserService, PartieExacteRepository partieExacteRepository, TypeSecondaireRepository typeSecondaireRepository){

        this.repository = repository;
        this.authenticatedUserService = authenticatedUserService;
        this.partieExacteRepository = partieExacteRepository;
        this.typeSecondaireRepository = typeSecondaireRepository;
    }

    @GetMapping("tasks")
    public List<Task> tasksList(){
        return authenticatedUserService.getAuthenticatedUser().getTasks();
    }

    @GetMapping("tasks/{taskId}")
    public Task taskById(@PathVariable String taskId){
        return repository.findById(Long.parseLong(taskId)).map(task -> {
            if (task.getUser().equals(authenticatedUserService.getAuthenticatedUser())) {
                if(task == null){
                    return new Task();
                }else {

                    return task;
                }
            }return new Task();


        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }



    @PostMapping("tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        List<PartieExacte> partieExactes = task.getPartiesExacte();
        List<TypeSecondaire> typeSecondaires = task.getTypeSecondaires();
        task.setPartiesExacte(null);
        task.setTypeSecondaires(null);

        ApplicationUser user = authenticatedUserService.getAuthenticatedUser();
        task.setUser(user);
        Task t = repository.saveAndFlush(task);

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