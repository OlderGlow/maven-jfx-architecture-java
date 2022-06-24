package eu.unareil.dal.jdbc;

import eu.unareil.bo.Pain;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PainJDBCImpl implements DAO<Pain> {

    private static final String SQL_SELECT_ALL = "SELECT * FROM produit where type = 'pain'";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM produit where refProd = ?";
    private static final String SQL_INSERT = "INSERT INTO produit (libelle, marque, prixUnitaire, qteStock, type, poids, dateLimiteConso) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE produit SET libelle = ?, marque = ?, prixUnitaire = ?, qteStock = ?, type = ?, poids = ?, dateLimiteConso = ? WHERE refProd = ?";
    private static final String SQL_DELETE = "DELETE FROM produit WHERE refProd = ?";

    @Override
    public void insert(Pain objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setDouble(3, objet.getPrixUnitaire());
            preparedStatement.setLong(4, objet.getQteStock());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setDouble(6, objet.getPoids());
            preparedStatement.setDate(7, Date.valueOf(objet.getDateLimiteConso()));
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
    public void update(Pain objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setDouble(3, objet.getPrixUnitaire());
            preparedStatement.setLong(4, objet.getQteStock());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setDouble(6, objet.getPoids());
            preparedStatement.setDate(7, Date.valueOf(objet.getDateLimiteConso()));
            preparedStatement.setLong(8, objet.getRefProd());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été mise à jour");
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }

    }

    @Override
    public void delete(Pain objet) throws DalException {
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
    public Pain selectById(long id) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Pain(resultSet.getLong("refProd"), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getFloat("prixUnitaire"), resultSet.getLong("qteStock"), resultSet.getDate("dateLimiteConso").toLocalDate(), resultSet.getInt("poids"));
            } else {
                throw new DalException("Aucun produit ne correspond à l'id " + id);
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
    }

    @Override
    public List<Pain> selectAll() throws DalException {
        List<Pain> pains = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                pains.add(new Pain(resultSet.getLong("refProd"), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getFloat("prixUnitaire"), resultSet.getLong("qteStock"), resultSet.getDate("dateLimiteConso").toLocalDate(), resultSet.getInt("poids")));
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
        return pains;
    }
}
