package fr.univ.lorraine.houseSkipper.controller;


import fr.univ.lorraine.houseSkipper.auth.JWTAuthenticationFilter;
import fr.univ.lorraine.houseSkipper.exceptions.InvalidValidationTokenException;
import fr.univ.lorraine.houseSkipper.exceptions.UserEmailAlreadyExists;
import fr.univ.lorraine.houseSkipper.exceptions.UserNameNotFoundException;
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
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    //User
    private UserRepository UserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SkillRepository skillRepository;
    @Autowired
    private EmailServiceImpl notificationService;
    @Autowired
    private HttpServletRequest request;

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
            applicationUser.setEmailToken(RandomStringUtils.randomAlphanumeric(8));
            applicationUser.setIsValid(false);
            UserRepository.save(applicationUser);
            try {
                notificationService.sendConfirmationEmail(applicationUser);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @PutMapping("/")
    public void update(@RequestBody ApplicationUser applicationUser){
        ApplicationUser user = UserRepository.findByUsername(applicationUser.getUsername());
        if(user == null){
            throw new UserNameNotFoundException();
        }else {
            applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
            System.out.println(applicationUser.toString());
            UserRepository.save(applicationUser);
        }
    }

    @GetMapping("validateAccount/{emailToken}")
    public ApplicationUser validateAccount(@PathVariable String emailToken){
        System.out.println("============== "+emailToken);
        ApplicationUser user = UserRepository.findByEmailToken(emailToken);
        if(user != null){
                user.getUserAgents().add(request.getHeader("User-Agent"));
                System.out.println(request.getHeader("User-Agent"));
                user.setIsValid(true);
                UserRepository.save(user);
                user.setToken(JWTAuthenticationFilter.createTokenByUser(user));
                if(user.getSkills().isEmpty()){
                    skillRepository.save(new Skill("Gros Oeuvres", 1, user));
                    skillRepository.save(new Skill("Seconds Oeuvres", 1, user));
                    skillRepository.save(new Skill("Petits travaux de bricolage", 1, user));
                    skillRepository.save(new Skill("Petits travaux de jardinage", 1, user));
                    skillRepository.save(new Skill("Aménagement paysager", 1, user));
                }
                return user;
        }else{
            throw new InvalidValidationTokenException();
        }

    }
}
