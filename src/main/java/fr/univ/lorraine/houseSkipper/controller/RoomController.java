package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{houseName}/rooms")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Room> houseList(@PathVariable String houseName){
        return repository.findAllByHouse(houseName).stream().collect(Collectors.toList());
    }

}