package eu.unareil.dal.jdbc;

import eu.unareil.bo.Stylo;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StyloJDBCImpl implements DAO<Stylo> {

    private static final String SQL_SELECT_ALL = "SELECT * FROM produit WHERE type = 'stylo'";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM produit WHERE refProd = ?";
    private static final String SQL_INSERT = "INSERT INTO produit (refProd, libelle, marque, prixUnitaire, type, qteStock, couleur, typeMine) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE produit SET libelle = ?, marque = ?, prixUnitaire = ?, qteStock = ?, couleur = ?, typeMine = ? WHERE refProd = ?";
    private static final String SQL_DELETE = "DELETE FROM produit WHERE refProd = ?";

    @Override
    public void insert(Stylo objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, objet.getRefProd());
            preparedStatement.setString(2, objet.getLibelle());
            preparedStatement.setString(3, objet.getMarque());
            preparedStatement.setFloat(4, objet.getPrixUnitaire());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setLong(6, objet.getQteStock());
            preparedStatement.setString(7, objet.getCouleur());
            preparedStatement.setString(8, objet.getTypeMine());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été ajoutée");
            } else {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    objet.setRefProd(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }

    }

    @Override
    public void update(Stylo objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setFloat(3, objet.getPrixUnitaire());
            preparedStatement.setLong(4, objet.getQteStock());
            preparedStatement.setString(5, objet.getCouleur());
            preparedStatement.setString(6, objet.getTypeMine());
            preparedStatement.setLong(7, objet.getRefProd());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été mise à jour");
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
    }

    @Override
    public void delete(Stylo objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, objet.getRefProd());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été supprimée");
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
    }

    @Override
    public Stylo selectById(long id) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Stylo(resultSet.getLong("refProd"), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getFloat("prixUnitaire"), resultSet.getLong("qteStock"), resultSet.getString("couleur"), resultSet.getString("typeMine"));
            } else {
                throw new DalException("Aucun stylo ne correspond à l'id " + id);
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
    }

    @Override
    public List<Stylo> selectAll() throws DalException {
        List<Stylo> stylos = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stylos.add(new Stylo(resultSet.getLong("refProd"), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getFloat("prixUnitaire"), resultSet.getLong("qteStock"), resultSet.getString("couleur"), resultSet.getString("typeMine")));
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
        return stylos;
    }
}
