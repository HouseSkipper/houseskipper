package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.repositories.SubSkillRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkillCategoryControler {

    private SubSkillRepository subSkillRepository;

    public SkillCategoryControler(SubSkillRepository subSkillRepository){
        this.subSkillRepository = subSkillRepository;
    }


}
