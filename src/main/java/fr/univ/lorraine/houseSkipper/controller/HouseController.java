package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.House;
import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.model.UploadFileResponse;
import fr.univ.lorraine.houseSkipper.repositories.FileRepository;
import fr.univ.lorraine.houseSkipper.repositories.HouseRepository;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import fr.univ.lorraine.houseSkipper.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HouseController {

    private HouseRepository houseRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private AuthenticatedUserService authenticatedUserService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileRepository fileRepository;

    public HouseController(HouseRepository repository, RoomRepository roomRepository, UserRepository userRepository, AuthenticatedUserService authenticatedUserService) {
        this.houseRepository = repository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping("/add/house")
    public ResponseEntity<?> createHouse(@Valid @RequestBody House houseBody) {
        List<Room> rooms = houseBody.getRooms();
        houseBody.setRooms(null);

        ApplicationUser user = this.authenticatedUserService.getAuthenticatedUser();
        houseBody.setUser(user);
        House res = houseRepository.save(houseBody);

        for (Room room : rooms) {
            room.setHouse(houseBody);
            roomRepository.save(room);
        }

        return ResponseEntity.ok(res);
    }

    @PostMapping("/houses/uploadFile/{houseId}")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("description") String desc, @PathVariable String houseId) {
        try {
            String fileName = fileStorageService.storeFile(file, houseId, 1, desc);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();
            System.out.println(fileDownloadUri + "---------!!!" + fileName + "-----------!!");

            return new UploadFileResponse(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize(), file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            return new UploadFileResponse();
        }
    }

    @GetMapping("/houses")
    public Collection<House> houseList() {
        return houseRepository.findAll().stream().collect(Collectors.toList());
    }

    @GetMapping("/houses/house")
    public Collection<House> MyhousesList() {
        List<House> houses = houseRepository.findAllByUser(this.authenticatedUserService.getAuthenticatedUser());
        for(House e: houses){
            int taille = this.fileRepository.findAllByHouse(e).size();
            e.setNbDocument(taille);
        }
        return houses;
    }

    @GetMapping("/houses/{houseId}")
    public House Myhouse(@PathVariable Long houseId) {
        return houseRepository.findById(houseId).map(house -> {
            if (house.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()) {
                return house;
            } else {
                return null;
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la recherche d'une maison"));
    }

    @GetMapping("/houses/uploadFile/{houseId}")
    public Collection<UploadFileResponse> MyFilesHouse(@PathVariable Long houseId) {
        House house = houseRepository.findById(houseId).get();
        if(house.getUser() == this.authenticatedUserService.getAuthenticatedUser()){
            return this.fileRepository.findAllByHouse(house).stream().collect(Collectors.toList());
        } else {
            return new ArrayList<UploadFileResponse>();
        }

    }

    @GetMapping("/houses/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id){
        UploadFileResponse file = this.fileRepository.findById(id).get();
        if (file.getHouse().getUser() == this.authenticatedUserService.getAuthenticatedUser()){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getPic());
        }
        return ResponseEntity.status(404).body(null);
    }

    @PutMapping("/houses/{houseId}")
    public House modifierHouser(@Valid @RequestBody House houseBody) {
        return houseRepository.findById(houseBody.getId()).map(house -> {
            if (house.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()) {
                house = houseBody;
                house.setUser(this.authenticatedUserService.getAuthenticatedUser());
                houseRepository.save(house);
                return house;
            } else {
                return null;
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la modification d'une maison"));
    }

    @DeleteMapping("/houses/{houseId}")
    public ResponseEntity<?> deleteHouse(@PathVariable Long houseId) {
        return houseRepository.findById(houseId).map(house -> {
            if (house.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()) {
                houseRepository.delete(house);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la suppression"));
    }

    @DeleteMapping("/houses/file/{id}")
    public ResponseEntity<?> deleteFileHouse(@PathVariable Long id) {
        return this.fileRepository.findById(id).map(file -> {
            if(file.getHouse().getUser() == this.authenticatedUserService.getAuthenticatedUser()){
                //this.fileRepository.deleteById(file.getId());
                this.fileRepository.delete(file);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la suppression"));
    }

}