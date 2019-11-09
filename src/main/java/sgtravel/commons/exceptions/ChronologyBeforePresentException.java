package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when Dates entered are before present.
 */
public class ChronologyBeforePresentException extends ParseException {

    /**
     * Constructs the Exception.
     */
    public ChronologyBeforePresentException() {
        super(Messages.ERROR_DATE_BEFORE_NOW);
    }
}
