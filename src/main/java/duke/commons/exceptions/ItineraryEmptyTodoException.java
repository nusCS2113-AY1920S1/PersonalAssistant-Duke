package duke.commons.exceptions;

import duke.commons.Messages;

public class ItineraryEmptyTodoException extends DukeException {
    public ItineraryEmptyTodoException() {
        super(Messages.ITINERARY_EMPTY_TODOLIST);
    }
}
