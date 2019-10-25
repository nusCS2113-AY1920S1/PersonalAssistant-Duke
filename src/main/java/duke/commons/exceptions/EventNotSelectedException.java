package duke.commons.exceptions;

import duke.commons.Messages;

public class EventNotSelectedException extends DukeException {
    public EventNotSelectedException() {
        super(Messages.ERROR_EVENT_NOT_SELECTED);
    }
}
