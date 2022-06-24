package eu.unareil.bll;

import eu.unareil.bo.Auteur;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;
import eu.unareil.dal.DalException;

import java.util.List;

public class AuteurManager {
    private static volatile AuteurManager instance;
    private static DAO<Auteur> auteurDAO;

    public AuteurManager() {
        AuteurManager.auteurDAO = DAOFactory.getAuteurDAO();
    }

    public final static AuteurManager getInstance() {
        if (AuteurManager.instance == null) {
            synchronized (AuteurManager.class) {
                if (instance == null) {
                    AuteurManager.instance = new AuteurManager();
                }
            }
        }
        return AuteurManager.instance;
    }

    public List<Auteur> getAllAuteurs() throws BLLException {
        List<Auteur> auteurs;
        try {
            auteurs = AuteurManager.auteurDAO.selectAll();
        } catch (Exception e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return auteurs;
    }

    public Auteur getOneAuteur(long refAuteur) throws BLLException {
        Auteur auteur;
        try {
            auteur = AuteurManager.auteurDAO.selectById(refAuteur);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return auteur;
    }

    public void addAuteur(Auteur auteur) throws BLLException {
        if(auteur.getRefAuteur() != 0) {
            throw new BLLException("Auteur déjà existant");
        }
        validerAuteur(auteur);
        try {
            AuteurManager.auteurDAO.insert(auteur);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void updateAuteur(Auteur auteur) throws BLLException {
        if(auteur.getRefAuteur() == 0) {
            throw new BLLException("Auteur inexistant");
        }
        validerAuteur(auteur);
        try {
            AuteurManager.auteurDAO.update(auteur);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void deleteAuteur(Auteur auteur) throws BLLException {
        if(auteur.getRefAuteur() == 0) {
            throw new BLLException("Auteur inexistant");
        }
        try {
            AuteurManager.auteurDAO.delete(auteur);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    private void validerAuteur(Auteur auteur) throws BLLException {
        if(auteur.getNom() == null || auteur.getNom().isEmpty()) {
            throw new BLLException("Nom manquant");
        }
        if(auteur.getPrenom() == null || auteur.getPrenom().isEmpty()) {
            throw new BLLException("Prénom manquant");
        }
    }

}
