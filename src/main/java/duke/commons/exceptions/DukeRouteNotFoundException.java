package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeRouteNotFoundException extends DukeException {
    public DukeRouteNotFoundException() {
        super(Messages.ERROR_ROUTE_NOT_FOUND);
    }
}
