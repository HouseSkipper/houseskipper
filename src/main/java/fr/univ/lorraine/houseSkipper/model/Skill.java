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
    private List<SkillCategory> skillCategories = new ArrayList<>();


    public Skill(String type, int nb_works, ApplicationUser user) {
        this.setType(type);
        this.setNb_works(nb_works);
        this.setUser(user);
        this.createSubSkill(type);
    }

    public void average(){
        int res = 0;
        int size = 0;
        for(SkillCategory s: skillCategories){
            size += s.getSubSkills().size();
            for(SubSkill subS: s.getSubSkills()) {
                res = res + subS.getNb_works();
            }
        }
        this.nb_works = res / size;
    }

    private void createSubSkill(String name){
        switch (name){
            case "Gros œuvre":
                SkillCategory sc = new SkillCategory("Fondations", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Terrassement", sc));
                sc.getSubSkills().add(new SubSkill("Coulage fondations", sc));

                sc = new SkillCategory("Assainissement", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Collectif", sc));
                sc.getSubSkills().add(new SubSkill("Individuel", sc));

                sc = new SkillCategory("Soubassement", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Hérisson", sc));
                sc.getSubSkills().add(new SubSkill("Vide-sanitaire", sc));
                sc.getSubSkills().add(new SubSkill("Sous-sol", sc));

                sc = new SkillCategory("Edifice", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Elévation des murs", sc));
                sc.getSubSkills().add(new SubSkill("Pose de linteaux", sc));
                sc.getSubSkills().add(new SubSkill("Pose de poteaux", sc));
                sc.getSubSkills().add(new SubSkill("Pose de poutres", sc));
                sc.getSubSkills().add(new SubSkill("Pose de planchers", sc));
                sc.getSubSkills().add(new SubSkill("Réalisation pignons", sc));

                sc = new SkillCategory("Toiture", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Charpente fermette", sc));
                sc.getSubSkills().add(new SubSkill("Charpente traditionnelle", sc));
                sc.getSubSkills().add(new SubSkill("Couverture", sc));

                sc = new SkillCategory("Menuiseries extérieures", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Pose fenêtres", sc));
                sc.getSubSkills().add(new SubSkill("Pose portes", sc));
                break;
            case "Second œuvre":
                sc = new SkillCategory("Isolation", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Thermique", sc));
                sc.getSubSkills().add(new SubSkill("Phonique", sc));

                sc = new SkillCategory("Revêtements extérieurs", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Enduit", sc));
                sc.getSubSkills().add(new SubSkill("Bardage", sc));
                sc.getSubSkills().add(new SubSkill("Plaquette de parement", sc));
                sc.getSubSkills().add(new SubSkill("Mur végétal", sc));

                sc = new SkillCategory("Installations intérieures", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Chauffage", sc));
                sc.getSubSkills().add(new SubSkill("Electricité", sc));
                sc.getSubSkills().add(new SubSkill("Climatisation", sc));
                sc.getSubSkills().add(new SubSkill("Cloisons", sc));
                sc.getSubSkills().add(new SubSkill("Plomberie", sc));
                break;
            case "Aménagement paysager":
                sc = new SkillCategory("Conception", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Parc", sc));
                sc.getSubSkills().add(new SubSkill("Jardin", sc));
                sc.getSubSkills().add(new SubSkill("Décors", sc));

                sc = new SkillCategory("Réalisation", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Parc", sc));
                sc.getSubSkills().add(new SubSkill("Jardin", sc));
                sc.getSubSkills().add(new SubSkill("Terrain de sport", sc));
                sc.getSubSkills().add(new SubSkill("Milieu aquatique", sc));
                sc.getSubSkills().add(new SubSkill("Engazonnement", sc));

                sc = new SkillCategory("Entretien", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Parc", sc));
                sc.getSubSkills().add(new SubSkill("Jardin", sc));
                sc.getSubSkills().add(new SubSkill("Terrain de sport", sc));
                sc.getSubSkills().add(new SubSkill("Milieu aquatique", sc));
                sc.getSubSkills().add(new SubSkill("Espaces verts", sc));
                sc.getSubSkills().add(new SubSkill("Aménagement intérieur", sc));

                sc = new SkillCategory("Activités diverses", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Application de produits phytosanitaires", sc));
                sc.getSubSkills().add(new SubSkill("Taille des arbres", sc));
                sc.getSubSkills().add(new SubSkill("Débroussaillement", sc));
                sc.getSubSkills().add(new SubSkill("Rognage de souches", sc));
                sc.getSubSkills().add(new SubSkill("Dessouchage", sc));
                sc.getSubSkills().add(new SubSkill("Reboisement", sc));
                break;//push
            case "Petits travaux":
                sc = new SkillCategory("Bricolage", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Bricolage", sc));

                sc = new SkillCategory("Jardinage", this);
                this.skillCategories.add(sc);
                sc.getSubSkills().add(new SubSkill("Jardinage", sc));
                break;
        }
    }
}
