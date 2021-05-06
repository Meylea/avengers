package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
@Table(name = "ORGANISATION")
public class Organisation extends Personne {
    

    @ManyToOne(fetch = FetchType.LAZY)
    public Civil dirigeant;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    public List<Civil> membres;

    public Organisation() {
        super();
        this.membres = new ArrayList();
    }
}