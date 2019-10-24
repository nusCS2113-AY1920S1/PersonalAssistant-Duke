package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when date / time parsing fails.
 */
public class DukeDateTimeParseException extends DukeException {
    public DukeDateTimeParseException() {
        super(Messages.INVALID_FORMAT);
    }
}
