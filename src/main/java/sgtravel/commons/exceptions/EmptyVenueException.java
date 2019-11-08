package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error that there are no venues.
 */
public class EmptyVenueException extends DukeException {
    public EmptyVenueException() {
        super(Messages.ERROR_VENUE_EMPTY);
    }
}
