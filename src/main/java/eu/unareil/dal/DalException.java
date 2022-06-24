package eu.unareil.dal;

public class DalException extends Exception {

    public DalException() {
    }

    public DalException(String message) {
        super("Erreur DAL " + message);
    }

    public DalException(String message, Throwable cause) {
        super("Erreur DAL " + message, cause);
    }
}
