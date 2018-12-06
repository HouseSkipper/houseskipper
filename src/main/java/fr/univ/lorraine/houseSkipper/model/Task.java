package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Task {
    @Id @GeneratedValue
    private Long id;

    private @NonNull String username;
    private @NonNull String room;
    private @NonNull String description;
    private @NonNull String budget;
    private @NonNull Date start_date;
    private @NonNull String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //@JsonIgnoreProperties("houses")
    @JsonBackReference
    private @NonNull ApplicationUser user;

    public Task() {}

    public Task(String username, String room, String description, String budget, Date start_date, String status) {
        this.room = room;
        this.description = description;
        this.budget = budget;
        this.start_date = start_date;
        this.status = status;
        this.username = username;
    }
}
