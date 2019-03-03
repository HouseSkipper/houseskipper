package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="TypeSecondaire")
public class TypeSecondaire {

    @Id
    @GeneratedValue
    private Long id;

    private String typeS;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "typeS_task")
    private @NonNull
    Task task;


}
