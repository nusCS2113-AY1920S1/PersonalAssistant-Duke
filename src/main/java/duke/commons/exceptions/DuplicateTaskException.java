package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when a duplicate task is detected.
 */
public class DuplicateTaskException extends DukeException {
    public DuplicateTaskException() {
        super(Messages.ERROR_TASK_DUPLICATED);
    }
}
