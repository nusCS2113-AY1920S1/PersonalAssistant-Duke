package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error a specified itinerary is not found within the itineraryTable.
 */
public class NoSuchItineraryException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NoSuchItineraryException() {
        super(Messages.ERROR_ITINERARY_NOT_FOUND);
    }
}
