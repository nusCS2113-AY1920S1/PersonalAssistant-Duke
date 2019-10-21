package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeRouteNotFoundException extends DukeException {
    public DukeRouteNotFoundException() {
        super(Messages.ROUTE_NOT_FOUND);
    }
}
