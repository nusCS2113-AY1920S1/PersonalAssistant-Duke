package duke.commons.exceptions.parser;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;

public class DukeDateTimeParseException extends DukeException {
    public DukeDateTimeParseException() {
        super(Messages.INVALID_FORMAT);
    }
}
