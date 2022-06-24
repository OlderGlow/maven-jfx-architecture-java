package eu.unareil.bll;

import eu.unareil.bo.Pain;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;
import eu.unareil.dal.DalException;

import java.util.List;

public class PainManager {
    private static volatile PainManager instance;
    private static DAO<Pain> painDAO;

    public PainManager() {
        PainManager.painDAO = DAOFactory.getPainDAO();
    }

    public final static PainManager getInstance() {
        if (PainManager.instance == null) {
            synchronized (PainManager.class) {
                if (instance == null) {
                    PainManager.instance = new PainManager();
                }
            }
        }
        return PainManager.instance;
    }

    public List<Pain> getAllPains() throws BLLException {
        List<Pain> pains;
        try {
            pains = PainManager.painDAO.selectAll();
        } catch (Exception e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return pains;
    }

    public Pain getOnePain(long refPain) throws BLLException {
        Pain pain;
        try {
            pain = PainManager.painDAO.selectById(refPain);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return pain;
    }

    public void addPain(Pain pain) throws BLLException {
        if(pain.getRefProd() != 0) {
            throw new BLLException("Pain déjà existant");
        }
        validerPain(pain);
        try {
            PainManager.painDAO.insert(pain);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void updatePain(Pain pain) throws BLLException {
        if(pain.getRefProd() == 0) {
            throw new BLLException("Pain non existant");
        }
        validerPain(pain);
        try {
            PainManager.painDAO.update(pain);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void deletePain(Pain pain) throws BLLException {
        try {
            PainManager.painDAO.delete(pain);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public static void validerPain(Pain pain) throws BLLException {
        if(pain.getLibelle() == null || pain.getLibelle().isEmpty()) {
            throw new BLLException("Nom du pain manquant");
        }
        if(pain.getPrixUnitaire() == 0) {
            throw new BLLException("Prix du pain manquant");
        }
        if(pain.getPrixUnitaire() < 0) {
            throw new BLLException("Le prix du pain ne peut-être négatif");
        }
        if(pain.getPoids() == 0) {
            throw new BLLException("Poids du pain manquant");
        }
        if(pain.getPoids() < 0) {
            throw new BLLException("Le poids du pain ne peut-être négatif");
        }
        if(pain.getMarque() == null || pain.getMarque().isEmpty()) {
            throw new BLLException("Marque du pain manquante");
        }
    }


}
