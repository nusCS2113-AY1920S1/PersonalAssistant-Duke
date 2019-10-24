package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeDuplicateTaskException extends DukeException {
    public DukeDuplicateTaskException() {
        super(Messages.ERROR_TASK_DUPLICATED);
    }
}
