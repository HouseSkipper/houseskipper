package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="shortcuts")
public class Shortcut {
    @Id
    @GeneratedValue
    private Long id;

    private @NonNull
    String name;
    private int favoris;
    private @NonNull String link;
    private  String path_logo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //@JsonIgnoreProperties("rooms")
    @JsonBackReference
    private @NonNull ApplicationUser user;

}
