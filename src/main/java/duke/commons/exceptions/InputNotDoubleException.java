package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when file is not saved.
 */
public class InputNotDoubleException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public InputNotDoubleException() {
        super(Messages.PROMPT_NOT_DOUBLE);
    }
}
