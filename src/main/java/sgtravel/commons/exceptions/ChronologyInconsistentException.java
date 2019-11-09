package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when there is an inconsistency in dates given.
 */
public class ChronologyInconsistentException extends ParseException {

    /**
     * Constructs the Exception.
     */
    public ChronologyInconsistentException() {
        super(Messages.ERROR_DATE_INCONSISTENT);
    }
}
