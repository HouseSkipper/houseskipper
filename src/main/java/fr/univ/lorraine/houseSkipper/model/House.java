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
    private @NonNull String residence;
    private @NonNull int exterieur;
    private @NonNull String address;
    private @NonNull int postalCode;
    private @NonNull String city;
    private @NonNull String pays;
    private @NonNull int outsideSpace;
    private @NonNull int constructionYear;
    private @NonNull String standardType;
    private @NonNull String revetementExterieur;
    private @NonNull int surfaceToiture;
    private @NonNull String revetementToiture;
    private @NonNull String classeEnergetique;
    private @NonNull int gaz;
    private @NonNull int electricite;
    private @NonNull int panneauxPhoto;
    private @NonNull int eolienne;
    private @NonNull int surfaceExterieurAvant;
    private @NonNull int surfaceExterieurDroit;
    private @NonNull int surfaceExterieurGauche;
    private @NonNull int surfaceExterieurArriere;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("house")
    @JsonManagedReference
    private List<Room> rooms = new ArrayList<>();

    private @NonNull String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //@JsonIgnoreProperties("houses")
    @JsonBackReference
    private @NonNull ApplicationUser user;



}
