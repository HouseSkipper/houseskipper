package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Task {
    @Id @GeneratedValue
    private Long id;

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

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<UploadFileResponse> files = new ArrayList<>();

    public Task() {}

    public Task(String room, String description, String budget, Date start_date, String status) {
        this.room = room;
        this.description = description;
        this.budget = budget;
        this.start_date = start_date;
        this.status = status;
    }
}
