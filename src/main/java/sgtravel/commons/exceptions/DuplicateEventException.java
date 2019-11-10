package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when a duplicate task is detected.
 */
public class DuplicateEventException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public DuplicateEventException() {
        super(Messages.ERROR_EVENT_DUPLICATED);
    }
}
