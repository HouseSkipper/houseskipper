package fr.univ.lorraine.houseSkipper.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id @GeneratedValue
    private Long id;

    private @NonNull String room;
    private @NonNull String description;
    private @NonNull String budget;
    private @NonNull Date start_date;
    private @NonNull String status;

    public Task(String room, String description, String budget, Date start_date, String status) {
        this.room = room;
        this.description = description;
        this.budget = budget;
        this.start_date = start_date;
        this.status = status;
    }
}
