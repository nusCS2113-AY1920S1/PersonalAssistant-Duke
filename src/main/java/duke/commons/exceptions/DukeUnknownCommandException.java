package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when an unknown command is entered.
 */
public class DukeUnknownCommandException extends DukeException {
    public DukeUnknownCommandException() {
        super(Messages.UNKNOWN_COMMAND);
    }
}
