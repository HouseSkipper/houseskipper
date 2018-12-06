package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Skill;
import fr.univ.lorraine.houseSkipper.repositories.SkillRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SkillController {

    private SkillRepository skillRepository;


    public SkillController(SkillRepository skillRepository){
        this.skillRepository = skillRepository;

    }

    @PostMapping("/skills")
    public @Valid Skill createSkill(@Valid @RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    @PutMapping("/skills/{skillId}")
    public Skill updateSkill(@PathVariable Long profileId, @Valid @RequestBody Skill skillRequest) {
        return skillRepository.findById(profileId).map(skill -> {
            skill.setType(skillRequest.getType());
            skill.setNb_works(skillRequest.getNb_works());

            return skillRepository.save(skill);
        }).orElseThrow(() -> new ResourceNotFoundException("skillId " + profileId + " not found"));
    }

    @GetMapping("/skills")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Skill> skillsList(){
        return new ArrayList<>(skillRepository.findAll());
    }

    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long skillId) {
        return skillRepository.findById(skillId).map(skill -> {
            skillRepository.delete(skill);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("skillId " + skillId + " not found"));
    }



}
