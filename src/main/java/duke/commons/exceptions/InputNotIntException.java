package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when file is not saved.
 */
public class InputNotIntException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public InputNotIntException() {
        super(Messages.PROMPT_NOT_INT);
    }
}
