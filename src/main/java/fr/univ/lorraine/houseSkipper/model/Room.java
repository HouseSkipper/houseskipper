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
    private @NonNull int nbFenetre;
    private @NonNull int nbPorteFenetre;
    private @NonNull String typeChauffage;
    private @NonNull int nbRadiateur;
    private @NonNull int volet;
    private @NonNull int nbVolet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "house_id")
    //@JsonIgnoreProperties("rooms")
    @JsonBackReference
    private House house;

}
