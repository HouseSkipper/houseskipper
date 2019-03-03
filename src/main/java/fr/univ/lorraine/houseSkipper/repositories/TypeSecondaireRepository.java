package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.Task;
import fr.univ.lorraine.houseSkipper.model.TypeSecondaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeSecondaireRepository extends JpaRepository<TypeSecondaire, Long> {
    List<TypeSecondaire> findAllByTask(Task t);
}
