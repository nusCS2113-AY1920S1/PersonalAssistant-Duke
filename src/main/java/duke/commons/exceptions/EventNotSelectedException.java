package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when event is not selected.
 */
public class EventNotSelectedException extends DukeException {
    public EventNotSelectedException() {
        super(Messages.ERROR_EVENT_NOT_SELECTED);
    }
}
