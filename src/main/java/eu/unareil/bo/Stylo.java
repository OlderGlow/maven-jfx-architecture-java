package eu.unareil.bo;

public class Stylo extends Produit {
    private String couleur;
    private String typeMine;

    public Stylo(String couleur, String typeMine) {
        this.setCouleur(couleur);
        this.setTypeMine(typeMine);
    }

    public Stylo(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, String couleur, String typeMine) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.setCouleur(couleur);
        this.setTypeMine(typeMine);
    }

    public Stylo(String marque, String libelle, long qteStock, float prixUnitaire, String couleur, String typeMine) {
        super(libelle, marque, prixUnitaire, qteStock);
        this.setCouleur(couleur);
        this.setTypeMine(typeMine);
    }



    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getTypeMine() {
        return typeMine;
    }

    public void setTypeMine(String typeMine) {
        this.typeMine = typeMine;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(", ");
        sb.append("couleur=").append(couleur);
        sb.append(", typeMine=").append(typeMine);
        sb.append(']');
        return sb.toString();
    }
}
