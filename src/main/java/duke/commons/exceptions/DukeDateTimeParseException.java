package duke.commons.exceptions;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;

/**
 * Displays an error when date / time parsing fails.
 */
public class DukeDateTimeParseException extends DukeException {
    public DukeDateTimeParseException() {
        super(Messages.ERROR_INPUT_INVALID_FORMAT);
    }
}
