package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when a given time is in the future when it should not.
 */
public class ChronologyAfterPresentException extends ParseException {

    /**
     * Constructs the Exception.
     */
    public ChronologyAfterPresentException() {
        super(Messages.ERROR_DATE_AFTER_NOW);
    }
}
