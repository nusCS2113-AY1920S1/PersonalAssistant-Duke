package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when no such Route can be found.
 */
public class NoSuchRouteException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NoSuchRouteException() {
        super(Messages.ERROR_ROUTE_NOT_FOUND);
    }
}
