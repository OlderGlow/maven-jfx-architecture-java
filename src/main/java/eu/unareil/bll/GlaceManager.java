package eu.unareil.bll;

import eu.unareil.bo.Glace;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;
import eu.unareil.dal.DalException;

import java.util.List;

public class GlaceManager {

    private static volatile GlaceManager instance;
    private static DAO<Glace> glaceDAO;

    public GlaceManager() {
        GlaceManager.glaceDAO = DAOFactory.getGlaceDAO();
    }

    public final static GlaceManager getInstance() {
        if (GlaceManager.instance == null) {
            synchronized (GlaceManager.class) {
                if (instance == null) {
                    GlaceManager.instance = new GlaceManager();
                }
            }
        }
        return GlaceManager.instance;
    }

    public List<Glace> getAllGlaces() throws BLLException {
        List<Glace> glaces;
        try {
            glaces = GlaceManager.glaceDAO.selectAll();
        } catch (Exception e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return glaces;
    }

    public Glace getOneGlace(long refGlace) throws BLLException {
        Glace glace;
        try {
            glace = GlaceManager.glaceDAO.selectById(refGlace);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return glace;
    }

    public void addGlace(Glace glace) throws BLLException {
        if(glace.getRefProd() != 0) {
            throw new BLLException("Glace déjà existant");
        }
        validerGlace(glace);
        try {
            GlaceManager.glaceDAO.insert(glace);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void updateGlace(Glace glace) throws BLLException {
        validerGlace(glace);
        try {
            GlaceManager.glaceDAO.update(glace);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void deleteGlace(Glace glace) throws BLLException {
        try {
            GlaceManager.glaceDAO.delete(glace);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    static void validerGlace(Glace glace) throws BLLException {
        if(glace.getLibelle() == null || glace.getLibelle().isEmpty()) {
            throw new BLLException("Glace non existant");
        }
        if(glace.getParfum() == null || glace.getParfum().isEmpty()) {
            throw new BLLException("Parfum non existant");
        }
        if(glace.getPrixUnitaire() == 0) {
            throw new BLLException("Prix non existant");
        }
        if(glace.getPrixUnitaire() < 0) {
            throw new BLLException("Le prix ne peut pas être inférieur à 0");
        }
        if(glace.getQteStock() < 0) {
            throw new BLLException("La quantité ne peut pas être inférieure à 0");
        }
        if(glace.getTemperatureConservation() < 100 || glace.getTemperatureConservation() > 60) {
            throw new BLLException("La température de conservation ne peut pas être inférieure à 100 ou supérieure à 60");
        }
    }
}
