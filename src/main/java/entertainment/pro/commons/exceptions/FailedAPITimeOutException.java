package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Exception thrown when API fetch request fails due to time out.
 */
public class FailedAPITimeOutException extends Exceptions{
    public FailedAPITimeOutException(String message) {
        super(PromptMessages.API_TIME_OUT);
    }
}
