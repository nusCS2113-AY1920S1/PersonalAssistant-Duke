package duke.commons.exceptions;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;

public class EventSelectionOutOfBoundsException extends DukeException {
    public EventSelectionOutOfBoundsException() {
        super(Messages.ERROR_EVENT_OUT_OF_BOUND);
    }
}
