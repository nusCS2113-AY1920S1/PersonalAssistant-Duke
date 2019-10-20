package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeUnknownCommandException extends DukeException {
    public DukeUnknownCommandException() {
        super(Messages.UNKNOWN_COMMAND);
    }
}
