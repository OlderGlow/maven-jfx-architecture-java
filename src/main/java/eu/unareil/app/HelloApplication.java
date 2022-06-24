package eu.unareil.app;

import eu.unareil.bll.BLLException;
import eu.unareil.bll.ProduitManager;
import eu.unareil.bo.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    private final ProduitManager produitManager = ProduitManager.getInstance();
    private int choix = 0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        TilePane root = new TilePane();
        stage.setTitle("Produits");
        try {
            menu(root);
        } catch (BLLException e) {
            System.out.println(e.getMessage());
        }
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    public void menu(TilePane scene) throws BLLException {
        Produit produit = getProduit(choix);
        TilePane tilePane = scene;

        HBox ref = new HBox();
        Label reference = new Label("Référence :");
        reference.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        TextField textField = new TextField();
        textField.setDisable(true);
        textField.setText(String.valueOf(produit.getRefProd()));
        ref.getChildren().addAll(reference, textField);

        HBox nom = new HBox();
        Label libelle = new Label("Libellé :");
        libelle.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        TextField textField2 = new TextField();
        textField2.setText(produit.getLibelle());
        nom.getChildren().addAll(libelle, textField2);
        nom.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        HBox marque = new HBox();
        Label marqueproduit = new Label("Marque :");
        marqueproduit.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        TextField textField3 = new TextField();
        textField3.setText(produit.getMarque());
        marque.getChildren().addAll(marqueproduit, textField3);
        marque.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        HBox prix = new HBox();
        Label prixLibel = new Label("Prix :");
        prixLibel.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        TextField textField4 = new TextField();
        textField4.setText(String.valueOf(produit.getPrixUnitaire()));
        prix.getChildren().addAll(prixLibel, textField4);
        prix.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        HBox quantite = new HBox();
        Label quantiteLib = new Label("Quantité :");
        quantiteLib.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        TextField textField5 = new TextField();
        textField5.setText(String.valueOf(produit.getQteStock()));
        quantite.getChildren().addAll(quantiteLib, textField5);
        quantite.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        HBox typeProduit = new HBox();
        Label categorie = new Label("Type de produit :");
        categorie.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Pain", "CartePostale", "Glace", "Stylo");
        choiceBox.setValue(produit.getClass().getSimpleName());
        typeProduit.getChildren().addAll(categorie, choiceBox);
        typeProduit.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(ref, nom, marque, prix, quantite, typeProduit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new javafx.geometry.Insets(10));
        tilePane.getChildren().addAll(vBox);
        additionnalInfos(scene, produit);
        buttonMenu(scene, textField2, textField3, textField4, textField5, choiceBox);
    }

    public Produit getProduit(int id) {
        try {
            List<Produit> produits = produitManager.getAllProduits();
            return produits.get(id);
        } catch (BLLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Node buttonMenu(TilePane scene, TextField libelle, TextField marque, TextField prix, TextField quantite, ChoiceBox typeproduit) throws BLLException {
        TilePane tilePane = scene;
        HBox hBox = new HBox();
        Button button = new Button("Précédent");
        if (choix == 0) {
            button.setDisable(true);
        }
        button.setOnAction(event -> {
            if (choix > 0) {
                choix--;
                tilePane.getChildren().clear();
                try {
                    menu(scene);
                } catch (BLLException e) {
                    e.printStackTrace();
                }
            } else {
                button.setDisable(true);
            }
        });
        Button button2 = new Button("Nouveau");
        Button button3 = new Button("Enregistrer");
        button3.setOnAction(event -> {
            try {
                Produit produit = getProduit(choix);
                produit.setLibelle(libelle.getText());
                produit.setMarque(marque.getText());
                produit.setPrixUnitaire(Float.parseFloat(prix.getText()));
                produit.setQteStock(Long.parseLong(quantite.getText()));
                if(produit instanceof Pain){
                    Pain pain = (Pain) produit;
                }
                produitManager.updateProduit(produit);
            } catch (BLLException e) {
                e.printStackTrace();
            }
        });
        Button button4 = new Button("Supprimer");
        button4.setOnAction(event -> {
            try {
                produitManager.deleteProduit(getProduit(choix));
                tilePane.getChildren().clear();
                menu(scene);
                choix--;
            } catch (BLLException e) {
                e.printStackTrace();
            }
        });
        Button button5 = new Button("Suivant");
        if (choix == produitManager.getAllProduits().size() - 1) {
            button5.setDisable(true);
        }
        button5.setOnAction(event -> {
            try {
                if (choix < produitManager.getAllProduits().size() - 1) {
                    choix++;
                    tilePane.getChildren().clear();
                    try {
                        menu(scene);
                    } catch (BLLException e) {
                        e.printStackTrace();
                    }
                } else {
                    button5.setDisable(true);
                }
            } catch (BLLException e) {
                throw new RuntimeException(e);
            }
        });
        hBox.getChildren().addAll(button, button2, button3, button4, button5);
        tilePane.getChildren().add(hBox);
        return tilePane;
    }

    public Node additionnalInfos(TilePane scene, Produit produit) {
        if (produit instanceof Pain) {
            Pain pain = (Pain) produit;
            TilePane tilePane = scene;
            VBox vBox = new VBox();
            Label label = new Label("Poids :");
            TextField textField = new TextField();
            textField.setText(String.valueOf(pain.getPoids()));
            Label label2 = new Label("Date de péremption:");
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(pain.getDateLimiteConso());
            vBox.getChildren().addAll(label, textField, label2, datePicker);
            datePicker.setDisable(true);
            tilePane.getChildren().addAll(vBox);
            return tilePane;
        }
        if (produit instanceof Glace) {
            Glace glace = (Glace) produit;
            TilePane tilePane = scene;
            VBox vBox = new VBox();
            Label label = new Label("Parfum :");
            TextField textField = new TextField();
            textField.setText(String.valueOf(glace.getParfum()));
            Label label2 = new Label("Date de péremption:");
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(glace.getDateLimiteConso());
            Label label3 = new Label("Température de conservation :");
            TextField textField2 = new TextField();
            textField2.setText(String.valueOf(glace.getTemperatureConservation()));
            vBox.getChildren().addAll(label, textField, label2, datePicker, label3, textField2);
            tilePane.getChildren().addAll(vBox);
            return tilePane;
        }
        if (produit instanceof Stylo) {
            Stylo stylo = (Stylo) produit;
            TilePane tilePane = scene;
            VBox vBox = new VBox();
            Label label = new Label("Couleur :");
            TextField textField = new TextField();
            textField.setText(String.valueOf(stylo.getCouleur()));
            Label label2 = new Label("Type de mine :");
            RadioButton radioButton = new RadioButton("Crayon à papier");
            RadioButton radioButton2 = new RadioButton("Crayon à bille");
            RadioButton radioButton3 = new RadioButton("Feutre");
            ToggleGroup toggleGroup = new ToggleGroup();
            radioButton.setToggleGroup(toggleGroup);
            radioButton2.setToggleGroup(toggleGroup);
            radioButton3.setToggleGroup(toggleGroup);
            if (stylo.getTypeMine().equals("Crayon à papier")) {
                radioButton.setSelected(true);
            }
            if (stylo.getTypeMine().equals("Crayon à bille")) {
                radioButton2.setSelected(true);
            }
            if (stylo.getTypeMine().equals("Feutre")) {
                radioButton3.setSelected(true);
            }
            vBox.getChildren().addAll(label, textField, label2, radioButton, radioButton2, radioButton3);
            tilePane.getChildren().addAll(vBox);
            return tilePane;
        }
        if (produit instanceof CartePostale) {
            CartePostale cartePostale = (CartePostale) produit;
            TilePane tilePane = scene;

            VBox hBox = new VBox();
            Label label = new Label("Type :");
            RadioButton radioButton = new RadioButton("Portrait");
            RadioButton radioButton2 = new RadioButton("Paysage");
            ToggleGroup toggleGroup = new ToggleGroup();
            radioButton.setToggleGroup(toggleGroup);
            radioButton2.setToggleGroup(toggleGroup);
            if (cartePostale.getType().equals("Portrait")) {
                radioButton.setSelected(true);
            }
            if (cartePostale.getType().equals("Paysage")) {
                radioButton2.setSelected(true);
            }
            hBox.getChildren().addAll(label, radioButton, radioButton2);

            HBox hBox2 = new HBox();
            Label label2 = new Label("Auteur(s) :");
            // Créer une table de 2 colonnes contenant le nom et le prénom des auteurs
            TableView<Auteur> tableView = new TableView<>();
            tableView.setMaxSize(300, 100);
            TableColumn<Auteur, String> nomColonne = new TableColumn<>("Nom");
            nomColonne.setCellValueFactory(new PropertyValueFactory<>("nom"));
            TableColumn<Auteur, String> prenomColonne = new TableColumn<>("Prénom");
            prenomColonne.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            tableView.getColumns().addAll(nomColonne, prenomColonne);
            for (Auteur auteur : cartePostale.getLesAuteurs()) {
                tableView.getItems().add(auteur);
            }
            hBox2.getChildren().addAll(label2, tableView);
            VBox vBox = new VBox();
            vBox.getChildren().addAll(hBox, hBox2);
            tilePane.getChildren().addAll(vBox);
            return tilePane;
        }
        return scene;
    }

}