package duke.commons.exceptions;

import duke.commons.Messages;

public class NoSuchBusStopException extends DukeException {
    public NoSuchBusStopException() {
        super(Messages.ERROR_NO_SUCH_BUS_STOP);
    }
}
