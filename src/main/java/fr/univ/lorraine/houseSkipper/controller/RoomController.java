package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.House;
import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@RestController
public class RoomController {

    private RoomRepository repository;
    private AuthenticatedUserService authenticatedUserService;

    public RoomController(RoomRepository repository, AuthenticatedUserService authenticatedUserService){
        this.repository = repository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/rooms")
    public Collection<Room> roomList(){
        ApplicationUser user = this.authenticatedUserService.getAuthenticatedUser();
        List<House> houses = user.getHouses();
        List<Room> rooms = new ArrayList<>();
        for (House h: houses
             ) {
            rooms.addAll(h.getRooms());

        }
        for (Room r: rooms
             ) {
            System.out.println(r.getRoomName()+"---------------------------------------");
        }
        return rooms.stream().collect(Collectors.toList());
    }

    @GetMapping("/{houseName}/rooms")
    public Collection<Room> houseList(@PathVariable String houseName){
        return repository.findAllByHouse(houseName).stream().collect(Collectors.toList());
    }

}