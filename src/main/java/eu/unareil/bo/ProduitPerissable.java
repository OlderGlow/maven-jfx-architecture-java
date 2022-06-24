package eu.unareil.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class ProduitPerissable extends Produit {
    private LocalDate dateLimiteConso;

    public ProduitPerissable(LocalDate dateLimiteConso) {
        this.setDateLimiteConso(dateLimiteConso);
    }

    public ProduitPerissable(long refProd, String libelle, String marque, float prixUnitaire, long qteStock, LocalDate dateLimiteConso) {
        super(refProd, libelle, marque, prixUnitaire, qteStock);
        this.setDateLimiteConso(dateLimiteConso);
    }

    public ProduitPerissable(String libelle, String marque, float prixUnitaire, long qteStock, LocalDate dateLimiteConso) {
        super(libelle, marque, prixUnitaire, qteStock);
        this.setDateLimiteConso(dateLimiteConso);
    }

    public LocalDate getDateLimiteConso() {
        return dateLimiteConso;
    }

    public void setDateLimiteConso(LocalDate dateLimiteConso){
        this.dateLimiteConso = dateLimiteConso;
    };

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(", ");
        sb.append("dateLimiteConso=").append(dateLimiteConso.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return sb.toString();
    }
}
