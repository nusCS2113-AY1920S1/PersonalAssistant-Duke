package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error when no recent recommendations are found.
 */
public class NoRecentItineraryException extends SingaporeTravelException {

    /**
     * Constructs the Exception.
     */
    public NoRecentItineraryException() {
        super(Messages.ERROR_ITINERARY_NO_RECENT);
    }
}
