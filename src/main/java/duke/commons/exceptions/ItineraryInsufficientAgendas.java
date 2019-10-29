package duke.commons.exceptions;

import duke.commons.Messages;

public class ItineraryInsufficientAgendas extends DukeException {
    public ItineraryInsufficientAgendas() {
        super(Messages.ITINERARY_INSUFFICIENT_AGENDAS);
    }
}
