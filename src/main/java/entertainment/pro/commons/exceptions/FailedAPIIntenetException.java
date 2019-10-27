package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Exception thrown when API fetch request fails due to no internet connection.
 */
public class FailedAPIIntenetException extends Exceptions {
    public FailedAPIIntenetException(String message) {
        super(PromptMessages.API_OFFLINE);
    }
}
