package duke.commons.exceptions;

import duke.commons.Messages;

public class ItineraryInsufficientAgendasException extends DukeException {
    public ItineraryInsufficientAgendasException() {
        super(Messages.ITINERARY_INSUFFICIENT_AGENDAS);
    }
}
