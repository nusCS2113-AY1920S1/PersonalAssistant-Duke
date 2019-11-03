package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when a route is not found.
 */
public class RouteNotFoundException extends DukeException {
    public RouteNotFoundException() {
        super(Messages.ERROR_ROUTE_NOT_FOUND);
    }
}
