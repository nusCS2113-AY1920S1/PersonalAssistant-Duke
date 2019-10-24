package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when duplicate route is found.
 */
public class RouteNodeDuplicateException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public RouteNodeDuplicateException() {
        super(Messages.ERROR_ROUTE_NODE_DUPLICATE);
    }
}
