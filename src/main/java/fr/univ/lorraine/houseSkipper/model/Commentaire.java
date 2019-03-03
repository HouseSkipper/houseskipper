package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "commentaire")
public class Commentaire {
    @Id @GeneratedValue
    private Long id;

    private @NonNull String commentaire;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "commentaire_task")
    private @NonNull
    Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "phase_commentaire")
    private @NonNull
    Phase phase;
}
