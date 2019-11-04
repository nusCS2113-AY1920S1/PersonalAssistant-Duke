package duke.commons.exceptions;

import duke.commons.Messages;

public class ChronologyInconsistentException extends ParseException {
    public ChronologyInconsistentException() {
        super(Messages.ERROR_DATE_INCONSISTENT);
    }
}
