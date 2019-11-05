package duke.commons.exceptions;

import duke.commons.Messages;

public class HelpFailException extends DukeException {
    public HelpFailException() {
        super(Messages.ERROR_HELP_FAIL);
    }
}
