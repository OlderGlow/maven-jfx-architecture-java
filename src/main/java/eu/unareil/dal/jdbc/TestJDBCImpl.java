package eu.unareil.dal.jdbc;

import eu.unareil.bo.*;
import eu.unareil.dal.DalException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestJDBCImpl {
    public static void main(String[] args) {
        AuteurJDBCImpl auteurJDBC = new AuteurJDBCImpl();
        CartePostaleJDBCImpl cartePostaleJDBC = new CartePostaleJDBCImpl();
        PainJDBCImpl painJDBC = new PainJDBCImpl();
        GlaceJDBCImpl glaceJDBC = new GlaceJDBCImpl();
        StyloJDBCImpl styloJDBC = new StyloJDBCImpl();
        try {
            // Création d'un auteur
            Auteur auteur = new Auteur("Dupont Moretti", "Jean Philippe");
            Auteur auteur1 = new Auteur("DeLaFontaine", "Pierrick");
            auteurJDBC.insert(auteur);
            auteurJDBC.insert(auteur1);
            // Création d'une carte Postale
            List<Auteur> lesAuteurs = new ArrayList<Auteur>();
            lesAuteurs.add(auteur);
            CartePostale cartePostale = new CartePostale("Vacances","Dunkerque : LA CARTE POSTALE", 1000L, 1.3f, lesAuteurs, TypeCartePostale.Paysage);
            cartePostaleJDBC.insert(cartePostale);
            // Création d'un pain
            Pain pain = new Pain("Harris", "Pain de mie", 1000, 1000L, 0.90f);
            painJDBC.insert(pain);
            // Création d'un glace
            Glace glace = new Glace(LocalDate.now().plusDays(2), "Glace", "Glace", -3, "Chocolat", 500L, 3.0f);
            glaceJDBC.insert(glace);
            // Création d'un stylo
            Stylo stylo = new Stylo("Bic", "Stylo", 1000L, 0.90f, "bleu", "fine");
            styloJDBC.insert(stylo);

            // Afficher les produits
            System.out.println("Liste des produits");
            System.out.println(cartePostaleJDBC.selectAll());
            System.out.println(painJDBC.selectAll());
            System.out.println(glaceJDBC.selectAll());
            System.out.println(styloJDBC.selectAll());

            // Afficher les auteurs
            System.out.println("Liste des auteurs");
            System.out.println(auteurJDBC.selectAll());

            // Supprimer le stylo
            styloJDBC.delete(stylo);

            // Modifier le pain
            pain.setPrixUnitaire(1.5f);
            painJDBC.update(pain);

            // Afficher les produits
            System.out.println("Liste des produits - Modifiés :");
            System.out.println(cartePostaleJDBC.selectAll());
            System.out.println(painJDBC.selectAll());
            System.out.println(glaceJDBC.selectAll());
            System.out.println(styloJDBC.selectAll());

        } catch (DalException e) {
            e.printStackTrace();
        }
    }
}
