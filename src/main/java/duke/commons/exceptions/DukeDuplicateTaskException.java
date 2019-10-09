package duke.commons.exceptions;

import duke.commons.Messages;

public class DukeDuplicateTaskException extends DukeException {
    public DukeDuplicateTaskException() {
        super(Messages.DUPLICATED_TASK);
    }
}
