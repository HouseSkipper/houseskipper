package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Phase {

    @Id @GeneratedValue
    private Long id;
    private String phaseName;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubPhase> subPhase = new ArrayList<>();

    public Phase(int id, String phaseName){
        this.id = Long.parseLong(""+id);
        this.phaseName = phaseName;

    }

}
