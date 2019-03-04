package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class SkillCategory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private @NonNull String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private @NonNull Skill skill;

    @OneToMany(mappedBy = "skillCategory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubSkill> subSkills = new ArrayList<>();


    public SkillCategory(String name, Skill skill){
        this.name = name;
        this.skill = skill;
    }

}
