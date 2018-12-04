package fr.univ.lorraine.houseSkipper.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @NonNull String firstname;
    private @NonNull String lastname;
    @Column(unique = true)
    private @NonNull String username; // email
    private @NonNull String password;
    private @NonNull String telephone;
    private @NonNull String role;
    private String token;

    public ApplicationUser(){

    }

}
