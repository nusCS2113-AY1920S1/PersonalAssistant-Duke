package duke.commons.exceptions;

import duke.commons.Messages;

public class ItineraryFailCreationException extends DukeException {
    public ItineraryFailCreationException() {
        super(Messages.ITINERARY_FAIL_CREATION);
    }
}
