package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeTaskNotFoundException extends DukeException {
    public DukeTaskNotFoundException() {
        super(Messages.ERROR_TASK_NOT_FOUND);
    }
}
