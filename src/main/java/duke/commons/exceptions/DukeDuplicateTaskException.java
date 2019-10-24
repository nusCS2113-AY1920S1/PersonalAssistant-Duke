package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Represents a duplicate task error.
 */
public class DukeDuplicateTaskException extends DukeException {
    public DukeDuplicateTaskException() {
        super(Messages.DUPLICATED_TASK);
    }
}
