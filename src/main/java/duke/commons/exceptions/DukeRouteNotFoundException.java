package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Represents a route not found error.
 */
public class DukeRouteNotFoundException extends DukeException {
    public DukeRouteNotFoundException() {
        super(Messages.ROUTE_NOT_FOUND);
    }
}
