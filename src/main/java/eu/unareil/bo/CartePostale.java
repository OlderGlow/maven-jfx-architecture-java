package eu.unareil.bo;

import java.util.List;

public class CartePostale extends Produit{
    private String type;
    private List<Auteur> lesAuteurs;

    public CartePostale() {
    }

    public CartePostale(String type, List<Auteur> lesAuteurs) {
        this.setType(type);
        this.setLesAuteurs(lesAuteurs);
    }

    public CartePostale(long refProd, String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, TypeCartePostale type) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.setType(type.toString());
        this.setLesAuteurs(lesAuteurs);
    }

    public CartePostale(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, String type) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.type = type;
    }

    public CartePostale(String marque, String libelle, long qteStock, float prixUnitaire, List<Auteur> lesAuteurs, TypeCartePostale type) {
        super(libelle, marque, prixUnitaire, qteStock);
        this.setType(type.toString());
        this.setLesAuteurs(lesAuteurs);
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Auteur> getLesAuteurs() {
        return lesAuteurs;
    }

    public void setLesAuteurs(List<Auteur> lesAuteurs) {
        this.lesAuteurs = lesAuteurs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(", ");
        sb.append("auteurs=");
        for(Auteur auteur : lesAuteurs) {
            sb.append("auteur").append((lesAuteurs.indexOf(auteur))+1).append("=");
            sb.append(auteur.getNom()).append(" ").append(auteur.getPrenom()).append(", ");
        }
        sb.append("type=").append(type);
        sb.append(']');
        return sb.toString();
    }
}
