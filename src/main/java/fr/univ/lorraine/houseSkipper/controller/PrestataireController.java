package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.exceptions.EntrepriseEmailAlreadyExists;
import fr.univ.lorraine.houseSkipper.model.Prestataire;
import fr.univ.lorraine.houseSkipper.repositories.PrestataireRepository;
import fr.univ.lorraine.houseSkipper.service.EmailServiceImpl;
import fr.univ.lorraine.houseSkipper.service.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class PrestataireController {
    @Autowired
    PrestataireRepository prestataireRepository;
    @Autowired
    private EmailServiceImpl notificationService;

    public void PrestataireController(){

    }

    @PostMapping("/add/prestataire")
    public ResponseEntity<?> addPrestataire(@Valid @RequestBody Prestataire prestataire){
        Prestataire p = prestataireRepository.findByEmail(prestataire.getEmail());
        RandomString rs = new RandomString(8, ThreadLocalRandom.current());

        if (p!= null){
            throw new EntrepriseEmailAlreadyExists();
        }else{

            prestataire.setPassword(rs.nextString());
            Prestataire pr = prestataireRepository.save(prestataire);

            try {
                notificationService.sendConfirmationEmail(prestataire);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(pr);
        }
    }

}
