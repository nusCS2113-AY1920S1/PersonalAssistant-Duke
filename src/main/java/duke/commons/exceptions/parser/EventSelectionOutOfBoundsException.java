package duke.commons.exceptions.parser;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;

public class EventSelectionOutOfBoundsException extends DukeException {
    public EventSelectionOutOfBoundsException() {
        super(Messages.EVENT_SELECTION_OUT_OF_BOUNDS);
    }
}
