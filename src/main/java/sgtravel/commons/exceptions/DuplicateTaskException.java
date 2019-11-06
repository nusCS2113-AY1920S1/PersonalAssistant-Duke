package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error when a duplicate task is detected.
 */
public class DuplicateTaskException extends DukeException {
    public DuplicateTaskException() {
        super(Messages.ERROR_TASK_DUPLICATED);
    }
}
