package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when no such bus stop can be found.
 */
public class NoSuchBusStopException extends SingaporeTravelException {

    /**
     * Constructs the Exception.
     */
    public NoSuchBusStopException() {
        super(Messages.ERROR_BUS_STOP_NOT_FOUND);
    }
}
