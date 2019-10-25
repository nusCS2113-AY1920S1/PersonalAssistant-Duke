package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when file is not saved.
 */
public class InputNotIntegerException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public InputNotIntegerException() {
        super(Messages.PROMPT_NOT_INT);
    }
}
