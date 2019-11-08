package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class ChronologyInconsistentException extends ParseException {
    public ChronologyInconsistentException() {
        super(Messages.ERROR_DATE_INCONSISTENT);
    }
}
