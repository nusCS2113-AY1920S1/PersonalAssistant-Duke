package entertainment.pro.commons.exceptions;

/**
 * Exception thrown when API fetch request fails due to some unknown reasons.
 */
public class FailedAPIException extends Exceptions {
    public FailedAPIException(String message) {
        super(message);
    }
}
