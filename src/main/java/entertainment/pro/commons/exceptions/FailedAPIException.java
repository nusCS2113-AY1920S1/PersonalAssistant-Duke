package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Exception thrown when API fetch request fails due to some unknown reasons.
 */
public class FailedAPIException extends Exceptions {
    public FailedAPIException(String message) {
        super(PromptMessages.API_FAIL_GENERAL);
    }
}
