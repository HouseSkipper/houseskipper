package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    private @NonNull String firstname;
    private @NonNull String lastname;
    @Column(unique = true)
    private @NonNull String username; // email
    private @NonNull String password;
    private @NonNull String telephone;
    private @NonNull String role;
    private String token;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("user")
    @JsonManagedReference
    private List<House> houses = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("user")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("user")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

    public ApplicationUser(){

    }

}
