package duke.commons.exceptions;

import duke.commons.Messages;

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
