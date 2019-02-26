package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Historic;
import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HistoricController {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("historic/{userId}")
    public List<Historic> userHistorics(@PathVariable String userId){
        List<Historic> historics = new ArrayList<>();
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            historics.add(t.getHistoric());
        }
        return historics;
    }

    @GetMapping("historic/{month}")
    public List<Historic> userHistoricsByMonth(@PathVariable int month){
        List<Historic> historics = new ArrayList<>();
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            if(t.getHistoric().getDate().getMonth() == now.getMonth().getValue()){
                historics.add(t.getHistoric());
            }
        }
        return historics;
    }

    @GetMapping("historic/{year}")
    public List<Historic> userHistoricsByYaer(@PathVariable int year){
        List<Historic> historics = new ArrayList<>();
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            if(t.getHistoric().getDate().getYear() == now.getYear()){
                historics.add(t.getHistoric());
            }
        }
        return historics;
    }
}
