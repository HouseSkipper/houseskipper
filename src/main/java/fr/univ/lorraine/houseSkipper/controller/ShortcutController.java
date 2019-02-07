package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;
import fr.univ.lorraine.houseSkipper.model.Shortcut;
import fr.univ.lorraine.houseSkipper.repositories.ShortcutRepository;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class ShortcutController {

    private ShortcutRepository shortcutRepository;
    private UserRepository userRepository;
    private AuthenticatedUserService authenticatedUserService;

    public ShortcutController(ShortcutRepository repository, UserRepository userRepository, AuthenticatedUserService authenticatedUserService){
        this.shortcutRepository = repository;
        this.userRepository = userRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping("/shortcut")
    public @Valid Shortcut createShortcut(@Valid @RequestBody Shortcut shortcut) {

        ApplicationUser user =  this.authenticatedUserService.getAuthenticatedUser();
        shortcut.setUser(user);

        return shortcutRepository.save(shortcut);
    }

    @GetMapping("/shortcuts")
    public Collection<Shortcut> shortcutList(){
        ApplicationUser user =  this.authenticatedUserService.getAuthenticatedUser();
        return user.getShortcuts();
    }

    @GetMapping("/shortcuts/{shortcutId}")
    public Shortcut myShortcut(@PathVariable Long shortcutId){
        return shortcutRepository.findById(shortcutId).map(shortcut -> {
            if(shortcut.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()){
                return shortcut;
            }else{
                return null;
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la recherche d'un shortcut"));
    }

    @PutMapping("/shortcuts/{shortcutId}")
    public Shortcut editShortcut(@Valid @RequestBody Shortcut shortcutBody){
        return shortcutRepository.findById(shortcutBody.getId()).map(shortcut -> {
            if(shortcut.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()){
                shortcut = shortcutBody;
                shortcut.setUser(this.authenticatedUserService.getAuthenticatedUser());
                shortcutRepository.save(shortcut);
                return shortcut;
            }else{
                return null;
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la modification d'un shortcut"));
    }

    @DeleteMapping("/shortcuts/{shortcutId}")
    public ResponseEntity<?> deleteShortcut(@PathVariable Long shortcutId) {
        return shortcutRepository.findById(shortcutId).map(shortcut -> {
            if(shortcut.getUser().getId() == this.authenticatedUserService.getAuthenticatedUser().getId()){
                shortcutRepository.delete(shortcut);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().build();
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Erreur lors de la suppression du shortcut id : "+shortcutId));
    }

}