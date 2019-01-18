package fr.univ.lorraine.houseSkipper.controller;


import fr.univ.lorraine.houseSkipper.exceptions.UserEmailAlreadyExists;
import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.Skill;
import fr.univ.lorraine.houseSkipper.repositories.SkillRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.EmailServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository UserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SkillRepository skillRepository;
    @Autowired
    private EmailServiceImpl notificationService;

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
            throw new UserEmailAlreadyExists();
        }else {
            applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
            System.out.println(applicationUser.toString());
            applicationUser.setEmailToken(RandomStringUtils.randomAlphanumeric(32));
            UserRepository.save(applicationUser);
            skillRepository.save(new Skill("Gros Oeuvres", 0, applicationUser));
            skillRepository.save(new Skill("Seconds Oeuvres", 0, applicationUser));
            skillRepository.save(new Skill("Petits travaux de bricolage", 0, applicationUser));
            skillRepository.save(new Skill("Petits travaux de jardinage", 0, applicationUser));
            skillRepository.save(new Skill("Aménagement paysager", 0, applicationUser));
            try {
                notificationService.sendConfirmationEmail(applicationUser);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("validateAccount/{email_token}")
    public void validateAccount(@PathVariable String email_token){
        ApplicationUser user = UserRepository.findByEmailToken(email_token);

    }
}
