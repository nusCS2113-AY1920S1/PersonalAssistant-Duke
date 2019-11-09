package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when a given time is in the past when it should not.
 */
public class ChronologyBeforePresentException extends ParseException {

    /**
     * Constructs the Exception.
     */
    public ChronologyBeforePresentException() {
        super(Messages.ERROR_DATE_BEFORE_NOW);
    }
}
