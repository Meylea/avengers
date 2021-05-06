package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
@Table(name = "CIVIL")
public class Civil extends Personne {
    public String prenom;
    public String civilite;
    public String email;
    public String telephone;
    public Date dateNaissance;
    public String nationalite;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "membres")
    public List<Organisation> organisations;

    @OneToMany(mappedBy = "dirigeant")
    public List<Organisation> organisationsDirigees;

    public Civil() {
        super();
    }
}