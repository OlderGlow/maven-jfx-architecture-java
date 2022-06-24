package eu.unareil.dal.jdbc;

import eu.unareil.bo.Glace;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GlaceJDBCImpl implements DAO<Glace> {

    private static final String SQL_INSERT = "INSERT INTO produit (libelle, marque, qteStock, prixUnitaire, type, dateLimiteConso, parfum, temperatureConservation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE produit SET libelle = ?, marque = ?, qteStock = ?, prixUnitaire = ?, type = ?, dateLimiteConso = ?, parfum = ?, temperatureConservation = ? WHERE refProd = ?";
    private static final String SQL_DELETE = "DELETE FROM produit WHERE refProd = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM produit WHERE type = 'glace'";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM produit WHERE refProd = ?";

    @Override
    public void insert(Glace objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setLong(3, objet.getQteStock());
            preparedStatement.setFloat(4, objet.getPrixUnitaire());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setDate(6, Date.valueOf(objet.getDateLimiteConso()));
            preparedStatement.setString(7, objet.getParfum());
            preparedStatement.setInt(8, objet.getTemperatureConservation());
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
    public void update(Glace objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, objet.getLibelle());
            preparedStatement.setString(2, objet.getMarque());
            preparedStatement.setLong(3, objet.getQteStock());
            preparedStatement.setFloat(4, objet.getPrixUnitaire());
            preparedStatement.setString(5, objet.getClass().getSimpleName());
            preparedStatement.setDate(6, Date.valueOf(objet.getDateLimiteConso()));
            preparedStatement.setString(7, objet.getParfum());
            preparedStatement.setInt(8, objet.getTemperatureConservation());
            preparedStatement.setLong(9, objet.getRefProd());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été mise à jour");
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
    }

    @Override
    public void delete(Glace objet) throws DalException {
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
    public Glace selectById(long id) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Glace(resultSet.getLong("refProd"), resultSet.getDate("dateLimiteConso").toLocalDate(), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getInt("temperatureConservation"), resultSet.getString("parfum"), resultSet.getLong("qteStock"), resultSet.getFloat("prixUnitaire"));
            } else {
                throw new DalException("Aucun produit ne correspond à l'id " + id);
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
    }

    @Override
    public List<Glace> selectAll() throws DalException {
        List<Glace> glaces = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                glaces.add(new Glace(resultSet.getLong("refProd"), resultSet.getDate("dateLimiteConso").toLocalDate(), resultSet.getString("libelle"), resultSet.getString("marque"), resultSet.getInt("temperatureConservation"), resultSet.getString("parfum"), resultSet.getLong("qteStock"), resultSet.getFloat("prixUnitaire")));
            }
        } catch (SQLException e) {
            throw new DalException(e.getMessage());
        }
        return glaces;
    }
}
