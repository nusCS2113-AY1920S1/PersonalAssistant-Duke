package duke.commons.exceptions;

import duke.commons.Messages;

public class ChronologyBeforePresentException extends ParseException {
    public ChronologyBeforePresentException() {
        super(Messages.ERROR_DATE_BEFORE_NOW);
    }
}
