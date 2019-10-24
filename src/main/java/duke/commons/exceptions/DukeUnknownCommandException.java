package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Represents an unknown command error.
 */
public class DukeUnknownCommandException extends DukeException {
    public DukeUnknownCommandException() {
        super(Messages.UNKNOWN_COMMAND);
    }
}
