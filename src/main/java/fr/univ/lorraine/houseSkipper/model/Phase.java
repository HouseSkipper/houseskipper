package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="phase")
public class Phase {

    @Id @GeneratedValue
    private Long id;
    private String phaseName;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "phase_sub")
    private List<SubPhase> subPhase = new ArrayList<>();

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "phase_commentaire")
    private List<Commentaire> commentaires = new ArrayList<>();
/*
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonBackReference
    private Task task;
*/
@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "phase_task")
    private List<Task> tasks = new ArrayList<>();

    public Phase(){}

    public Phase(Long id, String phaseName){
        this.id = id;
        this.phaseName = phaseName;

    }

}
