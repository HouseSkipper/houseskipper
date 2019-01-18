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
    public ResponseEntity<?> createHouse(@Valid @RequestBody House houseBody) {
        List<Room> rooms = houseBody.getRooms();
        houseBody.setRooms(null);

        ApplicationUser user =  this.authenticatedUserService.getAuthenticatedUser();
        houseBody.setUser(user);
        House res = houseRepository.save(houseBody);

        for(Room room: rooms){
            room.setHouse(houseBody);
            roomRepository.save(room);
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/houses")
    public Collection<House> houseList(){
        return houseRepository.findAll().stream().collect(Collectors.toList());
    }

    @GetMapping("/houses/house")
    public Collection<House> MyhousesList(){
        return houseRepository.findAllByUser(this.authenticatedUserService.getAuthenticatedUser()).stream().collect(Collectors.toList());
    }

    @GetMapping("/houses/{houseId}")
    public House Myhouse(@PathVariable Long houseId){
        return houseRepository.findById(houseId).map(house -> {
            if(house.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()){
                return house;
            }else{
                return null;
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la recherche d'une maison"));
    }

    @PutMapping("/houses/{houseId}")
    public House modifierHouser(@Valid @RequestBody House houseBody){
        return houseRepository.findById(houseBody.getId()).map(house -> {
            if(house.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()){
                house = houseBody;
                house.setUser(this.authenticatedUserService.getAuthenticatedUser());
                houseRepository.save(house);
                return house;
            }else{
                return null;
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la modification d'une maison"));
    }

    @DeleteMapping("/houses/{houseId}")
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