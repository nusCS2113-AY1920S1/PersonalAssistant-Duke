package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when the API call fails.
 */
public class ApiException extends ParseException {

    /**
     * Constructs the Exception.
     */
    public ApiException() {
        super(Messages.ERROR_API_FAIL);
    }
}
