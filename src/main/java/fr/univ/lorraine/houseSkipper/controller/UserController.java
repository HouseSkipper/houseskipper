package fr.univ.lorraine.houseSkipper.controller;


import fr.univ.lorraine.houseSkipper.auth.JWTAuthenticationFilter;
import fr.univ.lorraine.houseSkipper.exceptions.InvalidValidationTokenException;
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

    //User
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
            applicationUser.setIsValid(false);
            UserRepository.save(applicationUser);
            try {
                notificationService.sendConfirmationEmail(applicationUser);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("validateAccount/{emailToken}")
    public ApplicationUser validateAccount(@PathVariable String emailToken){
        System.out.println("============== "+emailToken);
        ApplicationUser user = UserRepository.findByEmailToken(emailToken);
        if(user != null){
            if(user.getIsValid()){
                throw new InvalidValidationTokenException();
            }else{
                user.setIsValid(true);
                UserRepository.save(user);
                user.setToken(JWTAuthenticationFilter.createTokenByUser(user));
                skillRepository.save(new Skill("Gros Oeuvres", 0, user));
                skillRepository.save(new Skill("Seconds Oeuvres", 0, user));
                skillRepository.save(new Skill("Petits travaux de bricolage", 0, user));
                skillRepository.save(new Skill("Petits travaux de jardinage", 0, user));
                skillRepository.save(new Skill("Aménagement paysager", 0, user));
                return user;
            }
        }else{
            throw new InvalidValidationTokenException();
        }

    }
}
