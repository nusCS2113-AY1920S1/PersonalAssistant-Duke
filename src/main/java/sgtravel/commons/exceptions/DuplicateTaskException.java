package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when a duplicate task is detected.
 */
public class DuplicateTaskException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public DuplicateTaskException() {
        super(Messages.ERROR_TASK_DUPLICATED);
    }
}
