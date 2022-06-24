package eu.unareil.dal.jdbc;


import eu.unareil.dal.DalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCTools {

    public static Connection getConnection() throws DalException {

        final String SERVER = Settings.getProperty("server");
        final String PORT = Settings.getProperty("port");
        final String DATABASE = Settings.getProperty("database");
        final String USER = Settings.getProperty("user");
        final String PASSWORD = Settings.getProperty("password");
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER + "&password=" + PASSWORD);
        } catch (SQLException e) {
            throw new DalException("Une erreur est survenue : " + e);
        }
        return connection;
    }

}