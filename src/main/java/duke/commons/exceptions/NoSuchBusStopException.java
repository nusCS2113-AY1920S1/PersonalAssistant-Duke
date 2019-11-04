package duke.commons.exceptions;

import duke.commons.Messages;

public class NoSuchBusStopException extends DukeException {
    public NoSuchBusStopException() {
        super(Messages.ERROR_BUS_STOP_NOT_FOUND);
    }
}
