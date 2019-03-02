package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.PartieExacte;
import fr.univ.lorraine.houseSkipper.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartieExacteRepository extends JpaRepository<PartieExacte, Long> {
    List<PartieExacte> findAllByTask(Task t);
}
