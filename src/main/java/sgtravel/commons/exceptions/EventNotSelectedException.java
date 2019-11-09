package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when event is not selected.
 */
public class EventNotSelectedException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public EventNotSelectedException() {
        super(Messages.ERROR_EVENT_NOT_SELECTED);
    }
}
