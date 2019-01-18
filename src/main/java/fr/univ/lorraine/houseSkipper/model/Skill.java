package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="skill")
public class Skill {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private @NonNull String type;
    private @NonNull int nb_works;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //@JsonIgnoreProperties("houses")
    @JsonBackReference
    private @NonNull ApplicationUser user;


    public Skill(String type, int nb_works, ApplicationUser user) {
        this.setType(type);
        this.setNb_works(nb_works);
        this.setUser(user);
    }
}
