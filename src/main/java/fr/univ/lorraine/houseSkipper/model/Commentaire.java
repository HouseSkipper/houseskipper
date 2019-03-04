package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "commentaire")
@NoArgsConstructor
public class Commentaire {
    @Id @GeneratedValue
    private Long id;

    private @NonNull String commentaire;
    private @NonNull String auteur;
    private @NonNull String etat;
    private @NonNull
    LocalDate datec;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String phasec;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "commentaire_task")
    private
    Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "phase_commentaire")
    private
    Phase phase;
}
