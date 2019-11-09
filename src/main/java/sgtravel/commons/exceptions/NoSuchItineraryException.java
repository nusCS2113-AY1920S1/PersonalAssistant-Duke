package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when no such Itinerary can be found.
 */
public class NoSuchItineraryException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NoSuchItineraryException() {
        super(Messages.ERROR_ITINERARY_NOT_FOUND);
    }
}
