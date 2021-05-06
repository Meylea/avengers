package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Personne extends Model {
    // Une personne désigne une personne physique (civil) ou morale (organisation)
    public String nom;
    public String adresse;
    public String codePostal;
    public String ville;
    public String pays;
    public String commentaire;
    public Date dateAjout;
    public Date dateModif;
    public int nbIncidents;
    public int nbMissions;

    public Personne() {
        this.dateAjout = new Date();
        this.dateModif = new Date();
        this.nbIncidents = 0;
        this.nbMissions = 0;
    }
}