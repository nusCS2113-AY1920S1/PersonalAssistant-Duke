package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when nothing is found.
 */
public class NullResultException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NullResultException() {
        super(Messages.ERROR_ROUTE_NODE_DUPLICATE);
    }
}
