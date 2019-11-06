package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class HelpFailException extends DukeException {
    public HelpFailException() {
        super(Messages.ERROR_HELP_FAIL);
    }
}
