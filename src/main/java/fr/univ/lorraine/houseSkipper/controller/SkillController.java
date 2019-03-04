package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Skill;
import fr.univ.lorraine.houseSkipper.repositories.SkillRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SkillController {

    private SkillRepository skillRepository;
    private AuthenticatedUserService authenticatedUserService;


    public SkillController(SkillRepository skillRepository, UserRepository userRepository, AuthenticatedUserService authenticatedUserService){
        this.skillRepository = skillRepository;
        this.authenticatedUserService = authenticatedUserService;

    }

    @PostMapping("skills")
    public @Valid Skill createSkill(@Valid @RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    @PutMapping("skills/{skillId}")
    public Skill updateSkill(@PathVariable Long skillId, @Valid @RequestBody Skill skillRequest) {
        return skillRepository.findById(skillId).map(skill -> {
            skill.setType(skillRequest.getType());
            skill.setNb_works(skillRequest.getNb_works());

            return skillRepository.save(skill);
        }).orElseThrow(() -> new ResourceNotFoundException("skillId " + skillId + " not found"));
    }

    @GetMapping("skills/{skillId}")
    public Optional<Skill> getOneSkill(@PathVariable Long skillId){
        return skillRepository.findById(skillId);
    }

    @GetMapping("skills")
    public List<Skill> skillsList(){
        return authenticatedUserService.getAuthenticatedUser().getSkills();

    }

    @DeleteMapping("skills/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long skillId) {
        return skillRepository.findById(skillId).map(skill -> {
            skillRepository.delete(skill);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("skillId " + skillId + " not found"));
    }



}
