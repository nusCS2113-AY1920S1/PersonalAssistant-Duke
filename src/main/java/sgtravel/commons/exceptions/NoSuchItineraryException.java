package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error a specified itinerary is not found within the itineraryTable.
 */
public class NoSuchItineraryException extends DukeException {
    public NoSuchItineraryException() {
        super(Messages.ERROR_ITINERARY_NOT_FOUND);
    }
}
