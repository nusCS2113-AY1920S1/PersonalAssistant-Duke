package duke.commons.exceptions;

import duke.commons.Messages;

public class NoSuchBusServiceException extends DukeException {
    public NoSuchBusServiceException() {
        super(Messages.ERROR_BUS_SERVICE_NOT_FOUND);
    }
}
