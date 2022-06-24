package eu.unareil.bo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Achat {
    private long refAchat;
    private double montant;
    private List<Ligne> lignesAchat = new ArrayList<>();

    public Achat(Ligne ligne) {
        this.lignesAchat.add(ligne);
    }

    public Achat(long refAchat, double montant, List<Ligne> lignesAchat) {
        this.setRefAchat(refAchat);
        this.setMontant(montant);
        this.setLignesAchat(lignesAchat);
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Ligne getLigne(int index) {
        return lignesAchat.get(index);
    }

    public void ajouteLigne(Produit produit, int quantite) {
        Ligne ligne = new Ligne(produit, quantite);
        this.lignesAchat.add(ligne);
    }

    public void supprimeLigne(int index) {
        lignesAchat.remove(index);
    }

    public void modifieLigne(int index, int nouvelleQte) {
        Ligne ligne = lignesAchat.get(index);
        ligne.setQuantite(nouvelleQte);
    }

    public double calculMontant() {
        montant = 0;
        for (Ligne ligne : lignesAchat) {
            montant += ligne.getQte() * ligne.getProduit().getPrixUnitaire();
        }
        return montant;
    }

    public List<Ligne> getLignes() {
        return lignesAchat;
    }

    public long getRefAchat() {
        return refAchat;
    }

    public void setRefAchat(long refAchat) {
        this.refAchat = refAchat;
    }

    public List<Ligne> getLignesAchat() {
        return lignesAchat;
    }

    public void setLignesAchat(List<Ligne> lignesAchat) {
        this.lignesAchat = lignesAchat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        DecimalFormat df = new DecimalFormat("#0.00");
        sb.append("Achat : ").append("\n");
        for (Ligne ligne : lignesAchat) {
            sb.append("\n");
            sb.append("ligne ").append((lignesAchat.indexOf(ligne)) + 1).append(" : ");
            sb.append(ligne.toString());
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("Total de l'achat : ").append(df.format(calculMontant())).append(" euro").append((montant > 1) ? "s" : "");
        return sb.toString();
    }
}
