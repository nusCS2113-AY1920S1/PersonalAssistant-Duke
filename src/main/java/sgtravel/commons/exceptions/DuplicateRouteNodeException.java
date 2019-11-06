package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when duplicate route is found.
 */
public class DuplicateRouteNodeException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public DuplicateRouteNodeException() {
        super(Messages.ERROR_ROUTE_NODE_DUPLICATE);
    }
}
