package eu.unareil.dal.jdbc;

import eu.unareil.dal.DalException;

import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static Properties properties;
    private static void loadConfig() throws DalException {
        if(properties == null) {
            properties = new Properties();
            try {
                properties.load(Settings.class.getClassLoader().getResourceAsStream("settings.properties"));
            } catch (IOException e) {
                throw new DalException("Erreur lors de la lecture du fichier de configuration", e);
            }
        }
    }

    public static String getProperty(String key) throws DalException {
        loadConfig();
        return properties.getProperty(key);
    }
}
