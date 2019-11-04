package duke.commons.exceptions;

import duke.commons.Messages;

public class NoRecentItineraryException extends DukeException {
    public NoRecentItineraryException() {
        super(Messages.ITINERARY_NO_RECENT);
    }
}
