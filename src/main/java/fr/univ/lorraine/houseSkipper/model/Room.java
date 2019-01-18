package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name="room")
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private @NonNull String roomName;
    private @NonNull int space;
    private int nbFenetre;
    private  int nbPorteFenetre;
    private  String typeChauffage;
    private  int nbRadiateur;
    private  int volet;
    private  int nbVolet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    //@JsonIgnoreProperties("rooms")
    @JsonBackReference
    private @NonNull House house;

}
