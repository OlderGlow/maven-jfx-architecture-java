package eu.unareil.dal;

import eu.unareil.bo.*;
import eu.unareil.dal.jdbc.*;

public class DAOFactory {
    public static DAO<Pain> getPainDAO() {
        return new PainJDBCImpl();
    }
    public static DAO<Produit> getProduitDAO() {
        return new ProduitJDBCImpl();
    }
    public static DAO<Auteur> getAuteurDAO() {
        return new AuteurJDBCImpl();
    }
    public static DAO<Glace> getGlaceDAO() {
        return new GlaceJDBCImpl();
    }
    public static DAO<Stylo> getStyloDAO() {
        return new StyloJDBCImpl();
    }
    public static DAO<CartePostale> getCartePostaleDAO() {
        return new CartePostaleJDBCImpl();
    }
}
