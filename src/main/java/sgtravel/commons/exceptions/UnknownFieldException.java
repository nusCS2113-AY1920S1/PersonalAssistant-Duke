package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when an invalid field is queried.
 */
public class UnknownFieldException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public UnknownFieldException() {
        super(Messages.ERROR_FIELD_UNKNOWN);
    }
}
