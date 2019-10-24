package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Represents a date time parsing error.
 */
public class DukeDateTimeParseException extends DukeException {
    public DukeDateTimeParseException() {
        super(Messages.INVALID_FORMAT);
    }
}
