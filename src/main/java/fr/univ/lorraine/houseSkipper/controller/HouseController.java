package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.House;
import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.repositories.HouseRepository;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HouseController {

    private HouseRepository houseRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private AuthenticatedUserService authenticatedUserService;

    public HouseController(HouseRepository repository, RoomRepository roomRepository, UserRepository userRepository, AuthenticatedUserService authenticatedUserService){
        this.houseRepository = repository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping("/add/house")
    @CrossOrigin(origins = "http://localhost:4200")
    public House createHouse(@Valid @RequestBody House houseBody) {
        List<Room> rooms = houseBody.getRooms();
        houseBody.setRooms(null);

        ApplicationUser user =  this.authenticatedUserService.getAuthenticatedUser();
        houseBody.setUser(user);
        House res = houseRepository.save(houseBody);

        for(Room room: rooms){
            room.setHouse(houseBody);
            roomRepository.save(room);
        }

        return res;
    }

    @GetMapping("/houses")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<House> houseList(){
        return houseRepository.findAll().stream().collect(Collectors.toList());
    }

    @GetMapping("/houses/house")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<House> MyhousesList(){
        return houseRepository.findAllByUser(this.authenticatedUserService.getAuthenticatedUser()).stream().collect(Collectors.toList());
    }

    @DeleteMapping("/houses/{houseId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> deleteHouse(@PathVariable Long houseId) {
        return houseRepository.findById(houseId).map(house -> {
            if(house.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()){
                houseRepository.delete(house);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().build();
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la suppression"));
    }

}