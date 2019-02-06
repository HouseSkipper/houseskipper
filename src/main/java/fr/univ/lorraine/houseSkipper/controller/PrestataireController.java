package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Prestataire;
import fr.univ.lorraine.houseSkipper.repositories.PrestataireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PrestataireController {
    @Autowired
    PrestataireRepository prestataireRepository;

    public void PrestataireController(){

    }

    @PostMapping("/add/prestataire")
    public ResponseEntity<?> addPrestataire(@Valid @RequestBody Prestataire prestataire){
        Prestataire p = prestataireRepository.save(prestataire);
        System.out.println(ResponseEntity.ok(p));
        return ResponseEntity.ok(p);

    }

}
