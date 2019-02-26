package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class SubPhase {

    @Id @GeneratedValue
    private Long id;
    private String sPhaseName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Phase phase;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "historic_id", nullable = false)
    @JsonBackReference
    private Historic historic;

    public SubPhase(Long id, String sPhaseName){
        this.id = id;
        this.sPhaseName = sPhaseName;
    }
}
