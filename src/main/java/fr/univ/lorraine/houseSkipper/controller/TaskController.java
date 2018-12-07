package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController {

    private TaskRepository repository;
    private AuthenticatedUserService authenticatedUserService;

    public TaskController(TaskRepository repository, AuthenticatedUserService authenticatedUserService){

        this.repository = repository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("tasks")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Task> tasksList(){
        return authenticatedUserService.getAuthenticatedUser().getTasks();
    }

    @PostMapping("tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        ApplicationUser user = authenticatedUserService.getAuthenticatedUser();
        task.setUser(user);
        return repository.save(task);

    }

    @PutMapping("tasks/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @Valid @RequestBody Task taskRequest) {
        return repository.findById(taskId).map(task -> {
            if (task.getUser().equals(authenticatedUserService.getAuthenticatedUser())) {
                task.setRoom(taskRequest.getRoom());
                task.setDescription(taskRequest.getDescription());
                task.setBudget(taskRequest.getBudget());
                task.setStart_date(taskRequest.getStart_date());
                task.setStatus(taskRequest.getStatus());
                return repository.save(task);
            }return new Task();

        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }

    @DeleteMapping("tasks/{taskId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        return repository.findById(taskId).map(task -> {
            repository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }
}