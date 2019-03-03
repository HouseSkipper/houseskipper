package fr.univ.lorraine.houseSkipper.model;

import com.fasterxml.jackson.annotation.*;
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
    private @NonNull Date start_date;
    private  String type;
    private @NonNull String connaissance;
    private @NonNull String resultat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user_task")
    private @NonNull ApplicationUser user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "files_task")
    private List<UploadFileResponse> files = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "histo_task")
    private List<Historic> historics = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "partieE_task")
    private List<PartieExacte> partiesExacte = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "typeS_task")
    private List<TypeSecondaire> typeSecondaires = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "commentaire_task")
    private List<Commentaire> commentaires = new ArrayList<>();

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String currentPhase;
    /*

    @OneToOne(mappedBy = "task", cascade=CascadeType.ALL)
    private Phase status;
 */

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "phase_task")
    private @NonNull Phase status;

    public Task() {}

    public Task(String name, String partie, String description, String houseName, Date start_date, String type, String connaissance, String resultat) {
        this.nom = name;
        this.partie = partie;
        this.description = description;
        this.start_date = start_date;
        this.type = type;
        this.connaissance = connaissance;
        this.resultat = resultat;
        this.residence = houseName;
    }
}
