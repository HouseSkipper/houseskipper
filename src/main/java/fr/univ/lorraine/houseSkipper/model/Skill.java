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


    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubSkill> subSkills = new ArrayList<>();


    public Skill(String type, int nb_works, ApplicationUser user) {
        this.setType(type);
        this.setNb_works(nb_works);
        this.setUser(user);
        this.createSubSkill(type);
    }

    public void average(){
        int res = 0;
        for(SubSkill s: subSkills){
            res = res + s.getNb_works();
        }
        this.nb_works = res / this.subSkills.size();
    }

    private void createSubSkill(String name){
        switch (name){
            case "Gros œuvre":
                this.subSkills.add(new SubSkill("Fondations",this));
                this.subSkills.add(new SubSkill("Assainissement",this));
                this.subSkills.add(new SubSkill("Soubassement",this));
                this.subSkills.add(new SubSkill("Élévation (ou superstructure)",this));
                this.subSkills.add(new SubSkill("Murs porteurs",this));
                this.subSkills.add(new SubSkill("Poteaux",this));
                this.subSkills.add(new SubSkill("Poutres",this));
                this.subSkills.add(new SubSkill("Planchers",this));
                this.subSkills.add(new SubSkill("Charpente",this));
                this.subSkills.add(new SubSkill("Couverture",this));
                this.subSkills.add(new SubSkill("Menuiseries extérieures",this));
                break;
            case "Second œuvre":
                this.subSkills.add(new SubSkill("Isolation thermique",this));
                this.subSkills.add(new SubSkill("Isolation acoustique",this));
                this.subSkills.add(new SubSkill("Installation chauffage",this));
                this.subSkills.add(new SubSkill("Installation électrique",this));
                this.subSkills.add(new SubSkill("Installation climatisation",this));
                this.subSkills.add(new SubSkill("Revêtement extérieur",this));
                this.subSkills.add(new SubSkill("Cloisons intérieures",this));
                this.subSkills.add(new SubSkill("Plomberie",this));
                this.subSkills.add(new SubSkill("Menuiseries intérieures",this));
                this.subSkills.add(new SubSkill("Revêtements du sol",this));
                this.subSkills.add(new SubSkill("Revêtements muraux",this));
                break;
            case "Aménagement paysager":
                this.subSkills.add(new SubSkill("Conception de parcs",this));
                this.subSkills.add(new SubSkill("Conception de jardins",this));
                this.subSkills.add(new SubSkill("Aménagement de parc",this));
                this.subSkills.add(new SubSkill("Aménagement de jardins",this));
                this.subSkills.add(new SubSkill("Aménagement de terrains de sport",this));
                this.subSkills.add(new SubSkill("Aménagement de milieux aquatiques",this));
                this.subSkills.add(new SubSkill("Génie végétal",this));
                this.subSkills.add(new SubSkill("Engazonnement",this));
                this.subSkills.add(new SubSkill("Entretien de parcs",this));
                this.subSkills.add(new SubSkill("Entretien d’espaces verts",this));
                this.subSkills.add(new SubSkill("Entretien de jardins",this));
                this.subSkills.add(new SubSkill("Entretien de terrains de sport",this));
                this.subSkills.add(new SubSkill("Entretien des milieux aquatiques",this));
                this.subSkills.add(new SubSkill("Application de produits phytosanitaires",this));
                this.subSkills.add(new SubSkill("Taille des arbres",this));
                this.subSkills.add(new SubSkill("Débroussaillement",this));
                this.subSkills.add(new SubSkill("Rognage de souches",this));
                this.subSkills.add(new SubSkill("Dessouchage",this));
                this.subSkills.add(new SubSkill("Reboisement",this));
                this.subSkills.add(new SubSkill("Conception de décors",this));
                this.subSkills.add(new SubSkill("Entretien d’aménagements intérieurs",this));
                this.subSkills.add(new SubSkill("Maçonnerie paysagère",this));
                this.subSkills.add(new SubSkill("Eclairage",this));
                break;
            case "Petits travaux":
                this.subSkills.add(new SubSkill("Bricolage",this));
                this.subSkills.add(new SubSkill("Jardinage",this));
                break;
        }
    }
}
