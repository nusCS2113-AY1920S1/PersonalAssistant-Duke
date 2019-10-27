package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Exception thrown when API fetch request returns null or no results.
 */
public class FailedAPIEmptyException extends Exceptions {
    public FailedAPIEmptyException(String message) {
        super(PromptMessages.API_FAIL_EMPTY);
    }
}
