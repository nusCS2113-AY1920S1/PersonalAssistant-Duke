package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when there is no recent Itinerary.
 */
public class NoRecentItineraryException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NoRecentItineraryException() {
        super(Messages.ERROR_ITINERARY_NO_RECENT);
    }
}
