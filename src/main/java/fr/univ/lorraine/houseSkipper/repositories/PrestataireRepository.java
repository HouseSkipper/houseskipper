package fr.univ.lorraine.houseSkipper.repositories;

import fr.univ.lorraine.houseSkipper.model.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {
    public Prestataire findByEmail(String email);
}
