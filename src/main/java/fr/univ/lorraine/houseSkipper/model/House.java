package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.univ.lorraine.houseSkipper.repositories.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"files"})
@Table(name="house")
public class House{

    @Id
    @GeneratedValue
    private Long id;

    private @NonNull String houseName;
    private @NonNull String houseType;
    private @NonNull String residence;
    private int exterieur;
    private @NonNull String address;
    private @NonNull int postalCode;
    private @NonNull String city;
    private @NonNull String pays;
    private  int outsideSpace;
    private  int constructionYear;
    private @NonNull String standardType;
    private  String revetementExterieur;
    private  int surfaceToiture;
    private  String revetementToiture;
    private  String classeEnergetique;
    private  int gaz;
    private  int electricite;
    private  int panneauxPhoto;
    private  int eolienne;
    private  int surfaceExterieurAvant;
    private  int surfaceExterieurDroit;
    private  int surfaceExterieurGauche;
    private  int surfaceExterieurArriere;
    private int nbDocument;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonIgnoreProperties("house")
    @JsonManagedReference
    private List<Room> rooms = new ArrayList<>();

    private  String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //@JsonIgnoreProperties("houses")
    @JsonBackReference
    private @NonNull ApplicationUser user;

    @OneToMany(mappedBy = "house", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<UploadFileResponse> files = new ArrayList<>();



}
