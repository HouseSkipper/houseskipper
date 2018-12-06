package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController {

    private TaskRepository repository;

    public TaskController(TaskRepository repository){
        this.repository = repository;
    }

    @GetMapping("/tasks")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Task> tasksList(){
        return new ArrayList<>(repository.findAll());
    }

    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return repository.save(task);
    }

    @PutMapping("/tasks/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @Valid @RequestBody Task taskRequest) {
        return repository.findById(taskId).map(task -> {
            task.setRoom(taskRequest.getRoom());
            task.setDescription(taskRequest.getDescription());
            task.setBudget(taskRequest.getBudget());
            task.setStart_date(taskRequest.getStart_date());
            task.setStatus(taskRequest.getStatus());
            return repository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        return repository.findById(taskId).map(task -> {
            repository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }
}