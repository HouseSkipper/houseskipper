package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Historic {

    @Id @GeneratedValue
    private Long id;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private @NonNull SubPhase subPhase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private @NonNull Task task;

    public Historic(){}
}
