package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when duplicate route is found.
 */
public class DuplicateRouteException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public DuplicateRouteException() {
        super(Messages.ERROR_ROUTE_DUPLICATE);
    }
}
