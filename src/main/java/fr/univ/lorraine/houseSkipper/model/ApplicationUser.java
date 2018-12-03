package fr.univ.lorraine.houseSkipper.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull String username;
    private @NonNull String password;

    public ApplicationUser(){

    }

}
