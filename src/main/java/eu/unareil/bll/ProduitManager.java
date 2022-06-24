package eu.unareil.bll;

import eu.unareil.bo.*;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DAOFactory;
import eu.unareil.dal.DalException;

import java.util.List;

public class ProduitManager {
    private static ProduitManager instance;
    private static DAO<Produit> produitDAO;

    public ProduitManager() {
        ProduitManager.produitDAO = DAOFactory.getProduitDAO();
    }

    public final static ProduitManager getInstance() {
        if (ProduitManager.instance == null) {
            synchronized (ProduitManager.class) {
                if (instance == null) {
                    ProduitManager.instance = new ProduitManager();
                }
            }
        }
        return ProduitManager.instance;
    }

    public List<Produit> getAllProduits() throws BLLException {
        List<Produit> produits;
        try {
            produits = ProduitManager.produitDAO.selectAll();
        } catch (Exception e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return produits;
    }

    public Produit getOneProduit(long refProduit) throws BLLException {
        Produit produit;
        try {
            produit = ProduitManager.produitDAO.selectById(refProduit);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
        return produit;
    }

    public void addProduit(Produit produit) throws BLLException {
        if(produit.getRefProd() != 0) {
            throw new BLLException("Produit déjà existant");
        }
        validerProduit(produit);
        try {
            ProduitManager.produitDAO.insert(produit);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void updateProduit(Produit produit) throws BLLException {
        if(produit.getRefProd() == 0) {
            throw new BLLException("Produit inexistant");
        }
        validerProduit(produit);
        try {
            ProduitManager.produitDAO.update(produit);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    public void deleteProduit(Produit produit) throws BLLException {
        if(produit.getRefProd() == 0) {
            throw new BLLException("Produit inexistant");
        }
        try {
            ProduitManager.produitDAO.delete(produit);
        } catch (DalException e) {
            throw new BLLException("Erreur BLL : " + e.getMessage());
        }
    }

    private void validerProduit(Produit produit) throws BLLException {
        if(produit instanceof Pain) {
           PainManager painManager = PainManager.getInstance();
              PainManager.validerPain((Pain) produit);
        } else if(produit instanceof Glace) {
            GlaceManager glaceManager = GlaceManager.getInstance();
            GlaceManager.validerGlace((Glace) produit);
        } else if(produit instanceof Stylo) {
            StyloManager styloManager = StyloManager.getInstance();
            StyloManager.validerStylo((Stylo) produit);
        } else if(produit instanceof CartePostale){
            CartePostaleManager cartePostaleManager = CartePostaleManager.getInstance();
            CartePostaleManager.validerCartePostale((CartePostale) produit);
        } else {
            throw new BLLException("Produit non reconnu");
        }
    }



}
