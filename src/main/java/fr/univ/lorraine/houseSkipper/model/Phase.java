package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="phase")
public class Phase {

    @Id
    private Long id;
    private String phaseName;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubPhase> subPhase = new ArrayList<>();

    public Phase(){}

    public Phase(Long id, String phaseName){
        this.id = id;
        this.phaseName = phaseName;

    }

}
