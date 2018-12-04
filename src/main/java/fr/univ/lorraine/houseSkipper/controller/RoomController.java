package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RoomController {

    private RoomRepository repository;

    public RoomController(RoomRepository repository){
        this.repository = repository;
    }

    @GetMapping("/rooms")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Room> roomList(){
        return repository.findAll().stream().collect(Collectors.toList());
    }

}