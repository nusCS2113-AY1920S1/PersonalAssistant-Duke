package entertainment.pro.commons.exceptions;

/**
 * Exception for commands that are incomplete.
 */
public class MissingInfoException extends Exception {

    public MissingInfoException(String message) {
        super(message);
    }
}
