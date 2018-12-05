package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="house")
public class House{

    @Id
    @GeneratedValue
    private Long id;

    private @NonNull String houseName;
    private @NonNull String houseType;
    private @NonNull String address;
    private @NonNull int postalCode;
    private @NonNull String city;
    private @NonNull int livingSpace;
    private @NonNull int outsideSpace;
    private @NonNull int numberPieces;
    private @NonNull int constructionYear;
    private @NonNull String standardType;
    private @NonNull int standardTypeNumber;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("house")
    @JsonManagedReference
    private List<Room> rooms = new ArrayList<>();

    private @NonNull String heatingType;
    private @NonNull int amperage;
    private @NonNull String comment;
    private @NonNull String username;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //@JsonIgnoreProperties("houses")
    @JsonBackReference
    private @NonNull ApplicationUser user;



}
