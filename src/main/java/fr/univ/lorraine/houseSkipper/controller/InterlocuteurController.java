package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Interlocuteur;
import fr.univ.lorraine.houseSkipper.repositories.InterlocuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InterlocuteurController {

    @Autowired
    private InterlocuteurRepository interlocuteurRepository;

    @GetMapping("/interlocuteur")
    public List<Interlocuteur> interlocuteurList(){
        return this.interlocuteurRepository.findAll();
    }
}
