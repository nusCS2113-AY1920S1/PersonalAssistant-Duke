package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when startDate > endDate or endDate < startDate.
 */
public class ChronologyInconsistentException extends ParseException {

    /**
     * Constructs the Exception.
     */
    public ChronologyInconsistentException() {
        super(Messages.ERROR_DATE_INCONSISTENT);
    }
}
