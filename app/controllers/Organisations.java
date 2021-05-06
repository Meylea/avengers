package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Organisations extends Controller {

    public static void liste() {
        List<Organisation> organisations = Organisation.findAll();
        render(organisations);
    }

    public static void supprimer(long id) {
        Organisation organisation = Organisation.findById(id);
        organisation.delete();
        redirect("/Organisations/liste");
    }

    public static void formAjout(boolean error) {
        List<Civil> civils = Civil.findAll();
        String message = null;
        if (error) {
            message = "Veuillez remplir les champs requis";
        }
        render(civils, message);
    }

    public static void ajouter(String nom, long dirigeantId) {
        validation.required(nom);
        validation.required(dirigeantId);

        if (validation.hasErrors()) {
            params.flash();
            redirect("/Organisations/formAjout?error=true");
        }

        Civil dirigeant = Civil.findById(dirigeantId);

        Organisation organisation = new Organisation();
        organisation.nom = nom;
        organisation.dirigeant = dirigeant;
        organisation.membres.add(dirigeant);
        organisation.adresse = params.get("adresse");
        organisation.codePostal = params.get("codePostal");
        organisation.ville = params.get("ville");
        organisation.pays = params.get("pays");
        organisation.commentaire = params.get("commentaire");
        organisation.save();

        redirect("/Organisations/details?id=" + organisation.id);
    }

    public static void formEdit(long id, boolean error) {
        List<Civil> civils = Civil.findAll();
        Organisation organisation = Organisation.findById(id);
        if (organisation == null) {
            redirect("/Organisations/liste");
        }

        String message = null;
        if (error) {
            message = "Veuillez remplir les champs requis";
        }
        render(organisation, civils, message);
    }

    public static void editer(long id, String nom, long dirigeantId) {
        validation.required(nom);
        validation.required(dirigeantId);

        if (validation.hasErrors()) {
            params.flash();
            redirect("/Organisations/formEdit?error=true");
        }

        Civil dirigeant = Civil.findById(dirigeantId);

        Organisation organisation = Organisation.findById(id);
        organisation.nom = nom;
        organisation.dirigeant = dirigeant;
        if (! organisation.membres.contains(dirigeant)) {
            organisation.membres.add(dirigeant);
        }
        organisation.adresse = params.get("adresse");
        organisation.codePostal = params.get("codePostal");
        organisation.ville = params.get("ville");
        organisation.pays = params.get("pays");
        organisation.commentaire = params.get("commentaire");
        organisation.dateModif = new Date();
        organisation.save();

        redirect("/Organisations/liste");
    }

    public static void details(long id, int error) {
        Organisation organisation = Organisation.findById(id);
        List<Civil> civils = Civil.findAll();
        String message = null;
        if (error == 1) {
            message = "Ce membre est déjà dans la liste";
        } else if (error == 2) {
            message = "On ne peut supprimer le dirigeant de l'organisation";
        }
        render(organisation, civils, message);
    }

    public static void ajouterMembre(long organisationId, long civilId) {
        Organisation organisation = Organisation.findById(organisationId);
        Civil civil = Civil.findById(civilId);

        // Si le civil n'est pas déjà membre
        if (! organisation.membres.contains(civil)) {
            organisation.membres.add(civil);
            organisation.dateModif = new Date();
            organisation.save();
        } else {
            redirect("/Organisations/details?id=" + organisation.id + "&error=1");
        }

        redirect("/Organisations/details?id=" + organisation.id);
    }

    public static void retirerMembre(long organisationId, long civilId) {
        Organisation organisation = Organisation.findById(organisationId);
        Civil civil = Civil.findById(civilId);

        // On ne peut retirer le dirigeant de l'organisation de la liste des membres
        if (organisation.dirigeant != civil) { 
            organisation.membres.remove(civil);
            organisation.dateModif = new Date();
            organisation.save();
        } else {
            redirect("/Organisations/details?id=" + organisation.id + "&error=2");
        }

        redirect("/Organisations/details?id=" + organisation.id);
    }
}