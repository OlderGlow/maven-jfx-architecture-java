package eu.unareil.bll;

import eu.unareil.bo.CartePostale;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;
import eu.unareil.dal.DalException;

import java.util.List;

public class CartePostaleManager {
    private static volatile CartePostaleManager instance;
    private static DAO<CartePostale> cartePostaleDAO;

    public CartePostaleManager() {
        CartePostaleManager.cartePostaleDAO = DAOFactory.getCartePostaleDAO();
    }

    public final static CartePostaleManager getInstance() {
        if (CartePostaleManager.instance == null) {
            synchronized (CartePostaleManager.class) {
                if (instance == null) {
                    CartePostaleManager.instance = new CartePostaleManager();
                }
            }
        }
        return CartePostaleManager.instance;
    }

    public List<CartePostale> getAllCartePostales() throws BLLException {
        List<CartePostale> cartePostales;
        try {
            cartePostales = CartePostaleManager.cartePostaleDAO.selectAll();
        } catch (Exception e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return cartePostales;
    }

    public CartePostale getOneCartePostale(long refCartePostale) throws BLLException {
        CartePostale cartePostale;
        try {
            cartePostale = CartePostaleManager.cartePostaleDAO.selectById(refCartePostale);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return cartePostale;
    }

    public void addCartePostale(CartePostale cartePostale) throws BLLException {
        if(cartePostale.getRefProd() != 0) {
            throw new BLLException("CartePostale déjà existante");
        }
        validerCartePostale(cartePostale);
        try {
            CartePostaleManager.cartePostaleDAO.insert(cartePostale);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void updateCartePostale(CartePostale cartePostale) throws BLLException {
        if(cartePostale.getRefProd() == 0) {
            throw new BLLException("CartePostale inexistante");
        }
        validerCartePostale(cartePostale);
        try {
            CartePostaleManager.cartePostaleDAO.update(cartePostale);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void deleteCartePostale(CartePostale cartePostale) throws BLLException {
        try {
            CartePostaleManager.cartePostaleDAO.delete(cartePostale);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    static void validerCartePostale(CartePostale cartePostale) throws BLLException {
        if(cartePostale.getRefProd() == 0) {
            throw new BLLException("CartePostale inexistante");
        }
        if(cartePostale.getLibelle() == null || cartePostale.getLibelle().isEmpty()) {
            throw new BLLException("Nom de CartePostale vide");
        }
        if(cartePostale.getLesAuteurs() == null || cartePostale.getLesAuteurs().isEmpty()) {
            throw new BLLException("Une carte postale doit avoir au minimum un auteur");
        }
        if(cartePostale.getMarque() == null || cartePostale.getMarque().isEmpty()) {
            throw new BLLException("La marque de la carte postale est vide");
        }
        if(cartePostale.getType() == null || cartePostale.getType().isEmpty()) {
            throw new BLLException("Le type de la carte postale est vide");
        }
        if(cartePostale.getPrixUnitaire() <= 0) {
            throw new BLLException("Le prix de la carte doit être supérieur à 0");
        }
        if(cartePostale.getQteStock() < 0) {
            throw new BLLException("La quantité de stock de la carte doit être supérieur à 0");
        }
    }
}
