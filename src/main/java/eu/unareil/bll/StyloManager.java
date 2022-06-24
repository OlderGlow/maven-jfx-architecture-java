package eu.unareil.bll;

import eu.unareil.bo.Stylo;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;
import eu.unareil.dal.DalException;

import java.util.List;

public class StyloManager {
    private static volatile StyloManager instance;
    private static DAO<Stylo> styloDAO;

    public StyloManager() {
        StyloManager.styloDAO = DAOFactory.getStyloDAO();
    }

    public final static StyloManager getInstance() {
        if (StyloManager.instance == null) {
            synchronized (StyloManager.class) {
                if (instance == null) {
                    StyloManager.instance = new StyloManager();
                }
            }
        }
        return StyloManager.instance;
    }

    public List<Stylo> getAllStylos() throws BLLException {
        List<Stylo> stylos;
        try {
            stylos = StyloManager.styloDAO.selectAll();
        } catch (Exception e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return stylos;
    }

    public Stylo getOneStylo(long refStylo) throws BLLException {
        Stylo stylo;
        try {
            stylo = StyloManager.styloDAO.selectById(refStylo);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return stylo;
    }

    public void addStylo(Stylo stylo) throws BLLException {
        if(stylo.getRefProd() != 0) {
            throw new BLLException("Stylo déjà existant");
        }
        validerStylo(stylo);
        try {
            StyloManager.styloDAO.insert(stylo);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void updateStylo(Stylo stylo) throws BLLException {
        if(stylo.getRefProd() == 0) {
            throw new BLLException("Stylo inexistant");
        }
        validerStylo(stylo);
        try {
            StyloManager.styloDAO.update(stylo);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void deleteStylo(Stylo stylo) throws BLLException {
        try {
            StyloManager.styloDAO.delete(stylo);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    static void validerStylo(Stylo stylo) throws BLLException {
        if (stylo.getRefProd() == 0) {
            throw new BLLException("Stylo inexistant");
        }
        if (stylo.getLibelle() == null || stylo.getLibelle().isEmpty()) {
            throw new BLLException("Nom du stylo manquant");
        }
        if (stylo.getPrixUnitaire() == 0) {
            throw new BLLException("Prix du stylo manquant");
        }
        if(stylo.getPrixUnitaire() < 0) {
            throw new BLLException("Le prix du stylo ne peut pas être négatif");
        }
        if(stylo.getQteStock() < 0) {
            throw new BLLException("La quantité en stock du stylo ne peut pas être négative");
        }
        if (stylo.getTypeMine() == null || stylo.getTypeMine().isEmpty()) {
            throw new BLLException("Le type de mine est manquant");
        }
        if(stylo.getCouleur() == null || stylo.getCouleur().isEmpty()) {
            throw new BLLException("La couleur du stylo est manquante");
        }
    }
}
