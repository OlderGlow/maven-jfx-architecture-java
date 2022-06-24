package eu.unareil.bo;

import java.time.LocalDate;

public class Glace extends ProduitPerissable{
    private String parfum;
    private int temperatureConservation;

    public Glace(LocalDate dateLimiteConso, String parfum, int temperatureConservation) {
        super(dateLimiteConso);
        this.setParfum(parfum);
        this.setTemperatureConservation(temperatureConservation);
    }

    public Glace(long refProd, LocalDate dateLimiteConso, String marque, String libelle, int temperatureConservation, String parfum, long qteStock, float prixUnitaire) {
        super(refProd, libelle, marque, prixUnitaire, qteStock, dateLimiteConso);
        this.setParfum(parfum);
        this.setTemperatureConservation(temperatureConservation);
    }

    public Glace(LocalDate dateLimiteConso, String marque, String libelle, int temperatureConservation, String parfum, long qteStock, float prixUnitaire) {
        super(libelle, marque, prixUnitaire, qteStock, dateLimiteConso);
        this.setParfum(parfum);
        this.setTemperatureConservation(temperatureConservation);
    }

    public String getParfum() {
        return parfum;
    }

    public void setParfum(String parfum) {
        this.parfum = parfum;
    }

    public int getTemperatureConservation() {
        return temperatureConservation;
    }

    public void setTemperatureConservation(int temperatureConservation) {
        this.temperatureConservation = temperatureConservation;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(super.toString()).append(", ");
        sb.append("parfum=").append(parfum);
        sb.append(", temperatureConservation=").append(temperatureConservation);
        sb.append(']');
        return sb.toString();
    }
}
