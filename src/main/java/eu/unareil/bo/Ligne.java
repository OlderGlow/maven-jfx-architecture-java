package eu.unareil.bo;

import java.text.DecimalFormat;

public class Ligne {
    private long refLigne;
    private int quantite;
    private Produit produit;

    public Ligne() {
    }

    public Ligne(Produit produit, int quantite) {
        this.setQuantite(quantite);
        this.setProduit(produit);
    }

    public Ligne(long refLigne, int quantite, Produit produit) {
        this.setRefLigne(refLigne);
        this.setQuantite(quantite);
        this.setProduit(produit);
    }

    public int getQte() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public long getRefLigne() {
        return refLigne;
    }

    public void setRefLigne(long refLigne) {
        this.refLigne = refLigne;
    }

    public int getQuantite() {
        return quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getPrix() {
        return this.getQte() * produit.getPrixUnitaire();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("#0.00");
        sb.append(this.getClass().getSimpleName());
        sb.append(" [");
        sb.append("produit=").append(produit);
        sb.append(", qte=").append(quantite);
        sb.append(", prix=").append(df.format(this.getPrix())).append(" euro").append((this.getPrix() > 1) ? "s" : "");
        sb.append(']');
        return sb.toString();
    }
}
