package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class NoSuchItineraryException extends DukeException {
    public NoSuchItineraryException() {
        super(Messages.ERROR_ITINERARY_NOT_FOUND);
    }
}
