package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class SubSkill {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private @NonNull String type;
    private @NonNull int nb_works;

    // DEPLOY
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private @NonNull SkillCategory skillCategory;

    public SubSkill(String name, SkillCategory skillCategory){
        this.type = name;
        this.nb_works = 1;
        this.skillCategory = skillCategory;
    }

}
