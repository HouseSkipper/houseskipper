package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
public class SubSkill {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private @NonNull String type;
    private @NonNull int nb_works;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private @NonNull Skill skill;

    public SubSkill(){}

    public SubSkill(String name, Skill skill){
        this.type = name;
        this.nb_works = 1;
        this.skill = skill;
    }

}
