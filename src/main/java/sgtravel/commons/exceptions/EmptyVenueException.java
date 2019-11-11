package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when there are no venues.
 */
public class EmptyVenueException extends SingaporeTravelException {

    /**
     * Constructs the Exception.
     */
    public EmptyVenueException() {
        super(Messages.ERROR_VENUE_EMPTY);
    }
}
