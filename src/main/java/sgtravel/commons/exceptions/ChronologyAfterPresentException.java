package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class ChronologyAfterPresentException extends ParseException {
    public ChronologyAfterPresentException() {
        super(Messages.ERROR_DATE_AFTER_NOW);
    }
}
