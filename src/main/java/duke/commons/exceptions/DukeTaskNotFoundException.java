package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when a task is not found.
 */
public class DukeTaskNotFoundException extends DukeException {
    public DukeTaskNotFoundException() {
        super(Messages.TASK_NOT_FOUND);
    }
}
