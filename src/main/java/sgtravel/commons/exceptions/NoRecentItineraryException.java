package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class NoRecentItineraryException extends DukeException {
    public NoRecentItineraryException() {
        super(Messages.ERROR_ITINERARY_NO_RECENT);
    }
}
