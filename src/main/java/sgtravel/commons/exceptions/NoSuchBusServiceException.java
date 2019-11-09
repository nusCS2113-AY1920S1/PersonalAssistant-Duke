package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when no such bus service can be found.
 */
public class NoSuchBusServiceException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NoSuchBusServiceException() {
        super(Messages.ERROR_BUS_SERVICE_NOT_FOUND);
    }
}
