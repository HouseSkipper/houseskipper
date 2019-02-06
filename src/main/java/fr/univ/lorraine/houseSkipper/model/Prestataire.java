package fr.univ.lorraine.houseSkipper.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Prestataire {
    @Id
    @GeneratedValue
    private Long id;

    private @NonNull
    String nom;
    private @NonNull String nomSociete;
    private @NonNull String zipCode;
    private @NonNull String email;
    private String commentaire;
}
