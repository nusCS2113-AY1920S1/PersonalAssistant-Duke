package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when a route cannot be generated.
 */
public class RouteGenerateFailException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public RouteGenerateFailException() {
        super(Messages.ERROR_ROUTE_GENERATE_FAIL);
    }
}
