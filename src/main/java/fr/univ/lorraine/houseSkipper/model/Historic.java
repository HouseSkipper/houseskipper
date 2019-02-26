package fr.univ.lorraine.houseSkipper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Historic {

    @Id @GeneratedValue
    private Long id;
    private Date date;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    private SubPhase subPhase;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    private Task task;
}
