package eu.unareil.bll;

import eu.unareil.bo.Auteur;

import java.util.List;

public class TestBLL {
    public static void main(String[] args) {
        List<Auteur> auteurs;

        AuteurManager auteurManager = AuteurManager.getInstance();
        try {
            System.out.println("---------- Affichage des auteurs ----------");
            System.out.print("\n");
            auteurs = auteurManager.getAllAuteurs();
            for (Auteur auteur : auteurs) {
                System.out.println(auteur);
            }
            System.out.println("----------- Ajout d'un auteur -----------");
            System.out.print("\n");
            Auteur auteur = new Auteur("Magnus", "Nouveau prenom");
            auteurManager.addAuteur(auteur);
            auteurs = auteurManager.getAllAuteurs();
            for (Auteur auteur2 : auteurs) {
                System.out.println(auteur2);
            }

            System.out.println("----------- Suppression d'un auteur -----------");
            System.out.print("\n");
            auteurManager.deleteAuteur(auteur);

            auteurs = auteurManager.getAllAuteurs();
            for (Auteur auteur2 : auteurs) {
                System.out.println(auteur2);
            }
            Auteur pierre = auteurManager.getOneAuteur(14);
            pierre.setPrenom("Pierre");
            auteurManager.updateAuteur(pierre);
            auteurs = auteurManager.getAllAuteurs();
            for (Auteur auteur2 : auteurs) {
                System.out.println(auteur2);
            }

        } catch (BLLException e) {
            System.out.println(e.getMessage());
        }
    }
}
