package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull String username;
    private @NonNull String password;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("user")
    @JsonManagedReference
    private List<House> houses = new ArrayList<>();

    public ApplicationUser(){

    }

}
