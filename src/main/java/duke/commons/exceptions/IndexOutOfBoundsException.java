package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when index query is out of bounds.
 */
public class IndexOutOfBoundsException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public IndexOutOfBoundsException() {
        super(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
    }
}
