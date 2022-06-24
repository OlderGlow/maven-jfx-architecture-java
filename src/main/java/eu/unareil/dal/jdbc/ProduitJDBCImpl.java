package eu.unareil.dal.jdbc;

import eu.unareil.bo.*;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitJDBCImpl implements DAO<Produit> {
    private static final String SQL_INSERT = "INSERT INTO produit (libelle, marque, prixUnitaire, qteStock, type) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE produit SET libelle = ?, marque = ?, prixUnitaire = ?, qteStock = ?, type = ? WHERE refProd = ?";
    private static final String SQL_DELETE = "DELETE FROM produit WHERE refProd = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM produit WHERE refProd = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM produit";
    private static final String SQL_ADD_RELATION_AUTEUR_CARTE_POSTALE = "INSERT INTO auteur_cartePostale (refAuteur, refCartePostale) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL_BY_CARTE_POSTALE = "SELECT * FROM produit WHERE refProd IN (SELECT refProd FROM auteur_cartePostale WHERE refCartePostale = ?)";
    @Override
    public void insert(Produit objet) throws DalException {
        if(objet instanceof Pain){
            PainJDBCImpl painJDBCImpl = new PainJDBCImpl();
            painJDBCImpl.insert((Pain) objet);
        } else if (objet instanceof CartePostale){
            CartePostaleJDBCImpl cartePostaleJDBCImpl = new CartePostaleJDBCImpl();
            cartePostaleJDBCImpl.insert((CartePostale) objet);
        } else if (objet instanceof Stylo){
            StyloJDBCImpl styloJDBCImpl = new StyloJDBCImpl();
            styloJDBCImpl.insert((Stylo) objet);
        } else if (objet instanceof Glace){
            GlaceJDBCImpl glaceJDBCImpl = new GlaceJDBCImpl();
            glaceJDBCImpl.insert((Glace) objet);
        }
    }

    @Override
    public void update(Produit objet) throws DalException {
       if(objet instanceof Pain){
           PainJDBCImpl painJDBCImpl = new PainJDBCImpl();
           painJDBCImpl.update((Pain) objet);
       } else if (objet instanceof CartePostale){
           CartePostaleJDBCImpl cartePostaleJDBCImpl = new CartePostaleJDBCImpl();
           cartePostaleJDBCImpl.update((CartePostale) objet);
       } else if (objet instanceof Stylo){
           StyloJDBCImpl styloJDBCImpl = new StyloJDBCImpl();
           styloJDBCImpl.update((Stylo) objet);
       } else if (objet instanceof Glace){
           GlaceJDBCImpl glaceJDBCImpl = new GlaceJDBCImpl();
           glaceJDBCImpl.update((Glace) objet);
       }
    }

    @Override
    public void delete(Produit objet) throws DalException {
        if(objet instanceof Pain){
            PainJDBCImpl painJDBCImpl = new PainJDBCImpl();
            painJDBCImpl.delete((Pain) objet);
        } else if (objet instanceof CartePostale){
            CartePostaleJDBCImpl cartePostaleJDBCImpl = new CartePostaleJDBCImpl();
            cartePostaleJDBCImpl.delete((CartePostale) objet);
        } else if (objet instanceof Stylo){
            StyloJDBCImpl styloJDBCImpl = new StyloJDBCImpl();
            styloJDBCImpl.delete((Stylo) objet);
        } else if (objet instanceof Glace){
            GlaceJDBCImpl glaceJDBCImpl = new GlaceJDBCImpl();
            glaceJDBCImpl.delete((Glace) objet);
        }
    }

    @Override
    public Produit selectById(long id) throws DalException {
        try (Connection connection = JDBCTools.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if(resultSet.getString("type").equals("pain")){
                    PainJDBCImpl painJDBCImpl = new PainJDBCImpl();
                    return painJDBCImpl.selectById(id);
                } else if (resultSet.getString("type").equals("cartePostale")){
                    CartePostaleJDBCImpl cartePostaleJDBCImpl = new CartePostaleJDBCImpl();
                    return cartePostaleJDBCImpl.selectById(id);
                } else if (resultSet.getString("type").equals("stylo")){
                    StyloJDBCImpl styloJDBCImpl = new StyloJDBCImpl();
                    return styloJDBCImpl.selectById(id);
                } else if (resultSet.getString("type").equals("glace")){
                    GlaceJDBCImpl glaceJDBCImpl = new GlaceJDBCImpl();
                    return glaceJDBCImpl.selectById(id);
                }
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection d'un produit", e);
        }
        return null;
    }

    @Override
    public List<Produit> selectAll() throws DalException {
        List<Produit> produits = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getString("type").equals("Pain")){
                    PainJDBCImpl painJDBCImpl = new PainJDBCImpl();
                    produits.add(painJDBCImpl.selectById(resultSet.getLong("refProd")));
                } else if (resultSet.getString("type").equals("CartePostale")){
                    CartePostaleJDBCImpl cartePostaleJDBCImpl = new CartePostaleJDBCImpl();
                    produits.add(cartePostaleJDBCImpl.selectById(resultSet.getLong("refProd")));
                } else if (resultSet.getString("type").equals("Stylo")){
                    StyloJDBCImpl styloJDBCImpl = new StyloJDBCImpl();
                    produits.add(styloJDBCImpl.selectById(resultSet.getLong("refProd")));
                } else if (resultSet.getString("type").equals("Glace")){
                    GlaceJDBCImpl glaceJDBCImpl = new GlaceJDBCImpl();
                    produits.add(glaceJDBCImpl.selectById(resultSet.getLong("refProd")));
                }
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection de tous les produits", e);
        }
        return produits;
    }
}
