package fr.univ.lorraine.houseSkipper.controller;

import fr.univ.lorraine.houseSkipper.model.Commentaire;
import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.repositories.CommentairesRepository;
import fr.univ.lorraine.houseSkipper.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentaireController {

    @Autowired
    CommentairesRepository commentairesRepository;
    @Autowired
    TaskRepository taskRepository;

    @PostMapping("commentaire/{id}")
    public Commentaire addComment(@PathVariable String id, @RequestParam("commentaires") Commentaire commentaire){
        Task task = this.taskRepository.findByNom(id).get(0);

        System.out.println("tskBudget : !!!!-----------------" + task.getPartiesExacte().size());
        commentaire.setTask(task);
        return commentairesRepository.save(commentaire);
    }
}
