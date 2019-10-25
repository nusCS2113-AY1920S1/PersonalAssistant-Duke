package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when an unknown command is given.
 */
public class DukeUnknownCommandException extends DukeException {

    /**
     * Constructs the exception.
     */
    public DukeUnknownCommandException() {
        super(Messages.ERROR_COMMAND_UNKNOWN);
    }
}
