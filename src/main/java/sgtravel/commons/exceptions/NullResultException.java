package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when no result can be found.
 */
public class NullResultException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NullResultException() {
        super(Messages.ERROR_RESULT_NOT_FOUND);
    }
}
