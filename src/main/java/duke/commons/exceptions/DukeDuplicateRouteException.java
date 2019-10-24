package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Displays an error when a duplicate route is found.
 */
public class DukeDuplicateRouteException extends DukeException {
    public DukeDuplicateRouteException() {
        super(Messages.DUPLICATED_ROUTE);
    }
}
