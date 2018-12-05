package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.House;
import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.repositories.HouseRepository;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HouseController {

    private HouseRepository houseRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;

    public HouseController(HouseRepository repository, RoomRepository roomRepository, UserRepository userRepository){
        this.houseRepository = repository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/houses")
    public House createHouse(@Valid @RequestBody House houseBody) {
        List<Room> rooms = houseBody.getRooms();
        houseBody.setRooms(null);
        //House house = new House();
        //house.setHouseName("test");
        ApplicationUser user =  this.userRepository.findByUsername(houseBody.getUsername());
        houseBody.setUser(user);
        House res = houseRepository.save(houseBody);

        /*
        Room room = new Room();
        room.setRoomName("cuisine");
        room.setHouse(house);
        */
        for(Room room: rooms){
            room.setHouse(houseBody);
            roomRepository.save(room);
        }


        //System.out.println(house1);
        //House house1 =  this.houseRepository.save(house);
        //house1.getRooms().get(0).setHouse(house1);
        return res;
    }

    @GetMapping("/houses")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<House> houseList(){
        return houseRepository.findAll().stream().collect(Collectors.toList());
    }

}