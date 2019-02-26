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
@Table(name = "task")
public class Task {
    @Id @GeneratedValue
    private Long id;
    private @NonNull String nom;
    private @NonNull String partie;
    private @NonNull String residence;
    private @NonNull String description;
    private @NonNull String partieExacte;
    private @NonNull Date start_date;
    private @NonNull String status;
    private @NonNull String type;
    private @NonNull String connaissance;
    private @NonNull String resultat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    //@JsonIgnoreProperties("houses")
    @JsonBackReference
    private @NonNull ApplicationUser user;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<UploadFileResponse> files = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Historic> historics = new ArrayList<>();

    public Task() {}

    public Task(String name, String partie, String description, String houseName, String partieExacte, Date start_date, String status, String type, String connaissance, String resultat) {
        this.nom = name;
        this.partie = partie;
        this.description = description;
        this.partieExacte = partieExacte;
        this.start_date = start_date;
        this.status = status;
        this.type = type;
        this.connaissance = connaissance;
        this.resultat = resultat;
        this.residence = houseName;
    }
}
