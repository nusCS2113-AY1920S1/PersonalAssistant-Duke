package duke.commons.exceptions;

import duke.commons.Messages;

public class ItineraryIncorrectCommandException extends DukeException {
    public ItineraryIncorrectCommandException() {
        super(Messages.ITINERARY_INCORRECT_COMMAND);
    }
}
