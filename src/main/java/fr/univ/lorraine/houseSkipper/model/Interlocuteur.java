package fr.univ.lorraine.houseSkipper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
public class Interlocuteur {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String picture;
    private String email;
    private String role;

    public Interlocuteur(){}

    public Interlocuteur(String name, String picture, String email, String role){
        this.name = name;
        this.picture = picture;
        this.email = email;
        this.role = role;
    }
}
