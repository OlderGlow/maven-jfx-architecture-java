package eu.unareil.dal.jdbc;

import eu.unareil.bo.Auteur;
import eu.unareil.dal.DAO;
import eu.unareil.dal.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuteurJDBCImpl implements DAO<Auteur> {

    private final static String SQL_INSERT = "INSERT INTO auteur (nom, prenom) VALUES (?, ?)";
    private final static String SQL_UPDATE = "UPDATE auteur SET nom = ?, prenom = ? WHERE id = ?";
    private final static String SQL_DELETE = "DELETE FROM auteur WHERE id = ?";
    private final static String SQL_SELECT_ALL = "SELECT id, nom, prenom FROM auteur";
    private final static String SQL_SELECT_BY_ID = "SELECT * FROM auteur WHERE id = ?";
    private static final CartePostaleJDBCImpl cartePostaleJDBCImpl = new CartePostaleJDBCImpl();

    @Override
    public void insert(Auteur objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, objet.getNom());
            preparedStatement.setString(2, objet.getPrenom());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                throw new DalException("Aucune ligne n'a été ajoutée");
            } else {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    objet.setRefAuteur(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de l'insertion d'un auteur", e);
        }
    }

    @Override
    public void update(Auteur objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, objet.getNom());
            preparedStatement.setString(2, objet.getPrenom());
            preparedStatement.setLong(3, objet.getRefAuteur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la mise à jour d'un auteur", e);
        }
    }

    @Override
    public void delete(Auteur objet) throws DalException {
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, objet.getRefAuteur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la suppression d'un auteur", e);
        }
    }

    @Override
    public Auteur selectById(long id) throws DalException {
        Auteur auteur = new Auteur();
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                auteur = new Auteur(resultSet.getLong("id"), resultSet.getString("nom"), resultSet.getString("prenom"));
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection d'un auteur", e);
        }
        return auteur;
    }

    @Override
    public List<Auteur> selectAll() throws DalException {
        List<Auteur> auteurs = new ArrayList<>();
        try (Connection connection = JDBCTools.getConnection(); PreparedStatement preparedStatement2 = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                            Auteur allAuteur = new Auteur(resultSet2.getLong("id"), resultSet2.getString("nom"), resultSet2.getString("prenom"));
                            auteurs.add(allAuteur);
            }
        } catch (SQLException e) {
            throw new DalException("Erreur lors de la sélection de tous les auteurs", e);
        }
        return auteurs;
    }
}
