package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeDuplicateRouteException extends DukeException {
    public DukeDuplicateRouteException() {
        super(Messages.ERROR_ROUTE_DUPLICATE);
    }
}
