package entertainment.pro.commons.exceptions;

/**
 * Exception thrown when API fetch request fails due to some unknown reasons.
 */
public class FailedApiException extends Exceptions {
    public FailedApiException(String message) {
        super(message);
    }
}
