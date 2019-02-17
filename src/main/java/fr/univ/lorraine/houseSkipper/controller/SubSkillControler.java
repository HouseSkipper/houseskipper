package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Skill;
import fr.univ.lorraine.houseSkipper.model.SubSkill;
import fr.univ.lorraine.houseSkipper.repositories.SubSkillRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SubSkillControler {

    private SubSkillRepository subSkillRepository;

    public SubSkillControler(SubSkillRepository subSkillRepository){
        this.subSkillRepository = subSkillRepository;
    }

    @PutMapping("skill/subskill/{subSkillId}")
    public SubSkill updateSkill(@PathVariable Long subSkillId, @Valid @RequestBody SubSkill subSkillRequest) {
        return this.subSkillRepository.findById(subSkillId).map(skill -> {
            skill.setNb_works(subSkillRequest.getNb_works());
            skill.getSkill().average();
            return this.subSkillRepository.save(skill);
        }).orElseThrow(() -> new ResourceNotFoundException("subSkill " + subSkillId + " not found"));
    }
}
