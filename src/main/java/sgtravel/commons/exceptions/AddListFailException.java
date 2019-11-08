package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class AddListFailException extends ParseException {
    public AddListFailException() {
        super(Messages.ERROR_ADDLIST_NAME_EMPTY);
    }
}
