package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when a duplicate task is detected.
 */
public class DukeDuplicateTaskException extends DukeException {
    public DukeDuplicateTaskException() {
        super(Messages.ERROR_TASK_DUPLICATED);
    }
}
