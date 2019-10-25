package duke.commons.exceptions;

import duke.commons.Messages;

public class EmptyVenueException extends DukeException {
    public EmptyVenueException() {
        super(Messages.ERROR_VENUE_EMPTY);
    }
}
