package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Represents a task not found error.
 */
public class DukeTaskNotFoundException extends DukeException {
    public DukeTaskNotFoundException() {
        super(Messages.TASK_NOT_FOUND);
    }
}
