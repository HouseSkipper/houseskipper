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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SkillController {

    private SkillRepository skillRepository;
    private UserRepository userRepository;
    private AuthenticatedUserService authenticatedUserService;


    public SkillController(SkillRepository skillRepository, UserRepository userRepository, AuthenticatedUserService authenticatedUserService){
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
        this.authenticatedUserService = authenticatedUserService;

    }

    @PostMapping("users/{userId}/skills")
    public @Valid Skill createSkill(@Valid @RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    @PutMapping("users/{userId}/skills/{skillId}")
    public Skill updateSkill(@PathVariable Long skillId, @Valid @RequestBody Skill skillRequest, @PathVariable String userId) {
        return skillRepository.findById(skillId).map(skill -> {
            skill.setType(skillRequest.getType());
            skill.setNb_works(skillRequest.getNb_works());

            return skillRepository.save(skill);
        }).orElseThrow(() -> new ResourceNotFoundException("skillId " + skillId + " not found"));
    }

    @GetMapping("users/{userId}/skills")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Skill> skillsList(@PathVariable Long userId){
        return authenticatedUserService.getAuthenticatedUser().getSkills();
    }

    @DeleteMapping("users/{userId}/skills/{skillId}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long skillId) {
        return skillRepository.findById(skillId).map(skill -> {
            skillRepository.delete(skill);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("skillId " + skillId + " not found"));
    }



}
