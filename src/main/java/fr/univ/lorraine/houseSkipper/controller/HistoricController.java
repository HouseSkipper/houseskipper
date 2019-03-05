package fr.univ.lorraine.houseSkipper.controller;


import fr.univ.lorraine.houseSkipper.model.Historic;
import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.service.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HistoricController {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @GetMapping("/historic")
    public List<Historic> userHistorics(){
        List<Historic> historics = new ArrayList<>();
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            t.setCurrentPhase(t.getStatus().getPhaseName());
            Historic h = t.getHistorics().get(t.getHistorics().size()-1);
                h.setCurrentSubPhase(h.getSubPhase().getSPhaseName());
                h.setCurrentPhase(h.getPhase().getPhaseName());
                historics.add(h);


        }
        return historics;
    }

    @GetMapping("/historic/month")
    public List<Historic> userHistoricsByMonth(){
        List<Historic> historics = new ArrayList<>();
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            Historic h = t.getHistorics().get(t.getHistorics().size()-1);
                if(h.getDate().getMonth().getValue() == now.getMonth().getValue()){
                    h.setCurrentSubPhase(h.getSubPhase().getSPhaseName());
                    h.setCurrentPhase(h.getPhase().getPhaseName());
                    historics.add(h);
                }


        }
        return historics;
    }

    @GetMapping("/historic/year")
    public List<Historic> userHistoricsByYaer(){
        List<Historic> historics = new ArrayList<>();
        for (Task t:
                authenticatedUserService.getAuthenticatedUser().getTasks()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            Historic h = t.getHistorics().get(t.getHistorics().size()-1);
                if(h.getDate().getYear() == now.getYear()){
                    h.setCurrentSubPhase(h.getSubPhase().getSPhaseName());
                    h.setCurrentPhase(h.getPhase().getPhaseName());
                    historics.add(h);
                }

        }
        return historics;
    }
}
