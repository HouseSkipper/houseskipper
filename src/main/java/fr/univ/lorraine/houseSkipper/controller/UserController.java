package fr.univ.lorraine.houseSkipper.controller;


import fr.univ.lorraine.houseSkipper.auth.JWTAuthenticationFilter;
import fr.univ.lorraine.houseSkipper.exceptions.InvalidValidationTokenException;
import fr.univ.lorraine.houseSkipper.exceptions.UserEmailAlreadyExists;
import fr.univ.lorraine.houseSkipper.exceptions.UserNameNotFoundException;
import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.House;
import fr.univ.lorraine.houseSkipper.model.Room;
import fr.univ.lorraine.houseSkipper.model.Skill;
import fr.univ.lorraine.houseSkipper.repositories.HouseRepository;
import fr.univ.lorraine.houseSkipper.repositories.RoomRepository;
import fr.univ.lorraine.houseSkipper.repositories.SkillRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.EmailServiceImpl;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    //User
    private UserRepository UserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SkillRepository skillRepository;
    private HouseRepository houseRepository;
    private RoomRepository roomRepository;
    @Autowired
    private EmailServiceImpl notificationService;
    @Autowired
    private HttpServletRequest request;

    public UserController(UserRepository UserRepository, SkillRepository skillRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder, HouseRepository houseRepository, RoomRepository roomRepository) {
        this.UserRepository = UserRepository;
        this.skillRepository = skillRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        ApplicationUser user = UserRepository.findByUsername(applicationUser.getUsername());
        if (user != null) {
            throw new UserEmailAlreadyExists();
        } else {
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

    @PutMapping("/update")
    public void update(@RequestBody ApplicationUser applicationUser) {
        ApplicationUser user = UserRepository.findByUsername(applicationUser.getUsername());
        if (user == null) {
            throw new UserNameNotFoundException();
        } else {
            //applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
            System.out.println(applicationUser.toString());
            UserRepository.save(applicationUser);
        }
    }

    @GetMapping("validateAccount/{emailToken}")
    public ApplicationUser validateAccount(@PathVariable String emailToken){
        ApplicationUser user = UserRepository.findByEmailToken(emailToken);
        if (user != null) {
            user.getUserAgents().add(request.getHeader("User-Agent"));
            System.out.println(request.getHeader("User-Agent"));
            user.setIsValid(true);
            UserRepository.save(user);
            user.setToken(JWTAuthenticationFilter.createTokenByUser(user));
            if (user.getSkills().isEmpty()) {
                House house = new House();
                house.setUser(user);
                house.setHouseName("Exemple de maison");
                house.setStandardType("T1");
                house.setHouseType("maison");
                house.setResidence("principale");
                house.setPays("France");
                house.setCity("Exemple Ville");
                house.setPostalCode(99999);
                house.setAddress("12 rue de l'exemple");
                house = this.houseRepository.save(house);

                Room r1 = new Room();
                r1.setHouse(house);
                r1.setRoomName("Cuisine");
                r1.setSpace(0);
                this.roomRepository.save(r1);

                Room r2 = new Room();
                r2.setHouse(house);
                r2.setRoomName("Salle de bain");
                r2.setSpace(0);
                this.roomRepository.save(r2);

                Room r3 = new Room();
                r3.setHouse(house);
                r3.setRoomName("Pièce d'exemple");
                r3.setSpace(0);
                this.roomRepository.save(r3);
                skillRepository.save(new Skill("Gros Oeuvres", 1, user));
                skillRepository.save(new Skill("Seconds Oeuvres", 1, user));
                skillRepository.save(new Skill("Petits travaux de bricolage", 1, user));
                skillRepository.save(new Skill("Petits travaux de jardinage", 1, user));
                skillRepository.save(new Skill("Aménagement paysager", 1, user));
            }
            return user;
        } else {
            throw new InvalidValidationTokenException();
        }
    }

    @PostMapping("/exists")
    @ResponseBody
    public ResponseEntity<?> getUserExists(@RequestBody ApplicationUser user){
        ApplicationUser u =  UserRepository.findByUsername(user.getUsername());
        if(u == null) {
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }
}
