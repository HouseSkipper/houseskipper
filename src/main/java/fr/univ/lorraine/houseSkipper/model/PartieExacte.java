package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="partieExacte")
public class PartieExacte {

    @Id
    @GeneratedValue
    private Long id;

    private String local;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "partieE_task")
    private @NonNull Task task;

    public PartieExacte(String local){
        this.local = local;
    }
}
