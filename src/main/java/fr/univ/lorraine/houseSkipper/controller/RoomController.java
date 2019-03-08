package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.House;
import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.repositories.HouseRepository;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
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
    private HouseRepository houseRepository;

    public RoomController(RoomRepository repository, AuthenticatedUserService authenticatedUserService, HouseRepository houseRepository){
        this.repository = repository;
        this.authenticatedUserService = authenticatedUserService;
        this.houseRepository = houseRepository;
    }

    @GetMapping("/rooms")
    public Collection<Room> roomList(){
        ApplicationUser user = this.authenticatedUserService.getAuthenticatedUser();
        List<House> houses = user.getHouses();
        List<Room> rooms = new ArrayList<>();
        for (House h: houses
             ) {
            for (Room r: h.getRooms()
                 ) {
                r.setRoomName(r.getRoomName() + " : " + h.getHouseName());
                rooms.add(r);
            }

        }

        return rooms.stream().collect(Collectors.toList());
    }

    @GetMapping("/rooms/{houseName}")
    public Collection<Room> houseList(@PathVariable String houseName){
        ApplicationUser user = this.authenticatedUserService.getAuthenticatedUser();
        List<House> houses = user.getHouses();
        List<Room> rooms = new ArrayList<>();
        System.out.println(houseName);
        for (House h: houses
        ) {
            System.out.println(houseName + h.getHouseName());
            if(houseName.equals(h.getHouseName())){
                for (Room r: h.getRooms()
                ) {
                    System.out.println(houseName + h.getHouseName() + "=======");
                    r.setRoomName(r.getRoomName());
                    rooms.add(r);
                }
            }
        }
        return rooms.stream().collect(Collectors.toList());
    }

}