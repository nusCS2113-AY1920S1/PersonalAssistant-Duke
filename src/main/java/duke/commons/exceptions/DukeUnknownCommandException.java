package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when an unknown command is entered.
 */
public class DukeUnknownCommandException extends DukeException {

    /**
     * Constructs the exception.
     */
    public DukeUnknownCommandException() {
        super(Messages.ERROR_COMMAND_UNKNOWN);
    }
}
