package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Civils extends Controller {

    public static void liste() {
        List<Civil> civils = Civil.findAll();
        render(civils);
    }

    public static void supprimer(long id) {
        Civil civil = Civil.findById(id);
        civil.delete();
        redirect("/civils/liste");
    }

    public static void formAjout(boolean error) {
        String message = null;
        if (error) {
            message = "Veuillez remplir les champs requis";
        }
        render(message);
    }

    public static void ajouter(String civilite, String nom, String prenom, Date dateNaissance, String nationalite) {
        validation.required(civilite);
        validation.required(nom);
        validation.required(prenom);
        validation.required(dateNaissance);
        validation.required(nationalite);

        if (validation.hasErrors()) {
            params.flash();
            redirect("/civils/formAjout?error=true");
        }

        Civil civil = new Civil();
        civil.civilite = civilite;
        civil.nom = nom;
        civil.prenom = prenom;
        civil.dateNaissance = dateNaissance;
        civil.nationalite = nationalite;
        civil.adresse = params.get("adresse");
        civil.codePostal = params.get("codePostal");
        civil.ville = params.get("ville");
        civil.pays = params.get("pays");
        civil.email = params.get("email");
        civil.telephone = params.get("telephone");
        civil.commentaire = params.get("commentaire");
        civil.save();
        redirect("/civils/liste");
    }

    public static void formEdit(long id, boolean error) {
        Civil civil = Civil.findById(id);
        if (civil == null) {
            redirect("/civils/liste");
        }

        String message = null;
        if (error) {
            message = "Veuillez remplir les champs requis";
        }

        render(civil);
    }

    public static void editer(long id, String civilite, String nom, String prenom, Date dateNaissance, String nationalite) {
        validation.required(civilite);
        validation.required(nom);
        validation.required(prenom);
        validation.required(dateNaissance);
        validation.required(nationalite);

        if (validation.hasErrors()) {
            params.flash();
            redirect("/civils/formEdit?error=true");
        }

        Civil civil = Civil.findById(id);
        civil.civilite = civilite;
        civil.nom = nom;
        civil.prenom = prenom;
        civil.dateNaissance = dateNaissance;
        civil.nationalite = nationalite;
        civil.adresse = params.get("adresse");
        civil.codePostal = params.get("codePostal");
        civil.ville = params.get("ville");
        civil.pays = params.get("pays");
        civil.email = params.get("email");
        civil.telephone = params.get("telephone");
        civil.commentaire = params.get("commentaire");
        civil.dateModif = new Date();
        civil.save();

        redirect("/civils/liste");
    }

    public static void details(long id) {
        Civil civil = Civil.findById(id);
        render(civil);
    }
}