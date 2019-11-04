package duke.commons.exceptions;

import duke.commons.Messages;

public class ItineraryNotFoundException extends DukeException {
    public ItineraryNotFoundException() {
        super(Messages.ITINERARY_NOT_FOUND);
    }
}
