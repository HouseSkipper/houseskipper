package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Historic {

    @Id @GeneratedValue
    private Long id;
    private LocalDate date;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String currentSubPhase;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String currentPhase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "histo_sPhase")
    private @NonNull SubPhase subPhase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "histo_Phase")
    private @NonNull Phase phase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "histo_task")
    private @NonNull Task task;

    public Historic(){}
}
