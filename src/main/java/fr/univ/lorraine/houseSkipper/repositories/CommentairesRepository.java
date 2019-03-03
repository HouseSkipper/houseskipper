package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentairesRepository extends JpaRepository<Commentaire, Long> {
}
