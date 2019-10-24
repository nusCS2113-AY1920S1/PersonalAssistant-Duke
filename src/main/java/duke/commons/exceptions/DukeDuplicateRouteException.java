package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Represents a duplicate route error.
 */
public class DukeDuplicateRouteException extends DukeException {
    public DukeDuplicateRouteException() {
        super(Messages.DUPLICATED_ROUTE);
    }
}
