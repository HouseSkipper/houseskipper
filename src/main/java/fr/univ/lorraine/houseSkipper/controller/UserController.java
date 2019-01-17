package fr.univ.lorraine.houseSkipper.controller;


import fr.univ.lorraine.houseSkipper.exceptions.UserEmailAlreadyExists;
import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.Skill;
import fr.univ.lorraine.houseSkipper.repositories.SkillRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository UserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SkillRepository skillRepository;

    public UserController(UserRepository UserRepository, SkillRepository skillRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.UserRepository = UserRepository;
        this.skillRepository = skillRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        ApplicationUser user = UserRepository.findByUsername(applicationUser.getUsername());
        if(user != null){
            throw new UserEmailAlreadyExists(String.format("User with email %s already exist!", applicationUser.getUsername()));
        }else {
            applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
            System.out.println(applicationUser.toString());
            UserRepository.save(applicationUser);
            skillRepository.save(new Skill("Jardinage", 0, applicationUser));
            skillRepository.save(new Skill("Plomberie", 0, applicationUser));
        }
    }
}
