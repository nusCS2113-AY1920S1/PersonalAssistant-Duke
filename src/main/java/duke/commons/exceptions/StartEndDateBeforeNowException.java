package duke.commons.exceptions;

import duke.commons.Messages;

public class StartEndDateBeforeNowException extends DukeException {
    public StartEndDateBeforeNowException() {
        super(Messages.START_END_DATE_BEFORE_NOW);
    }
}
