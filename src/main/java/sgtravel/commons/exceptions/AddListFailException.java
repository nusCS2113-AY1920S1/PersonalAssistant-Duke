package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when user does not specify new name while adding a recommendation.
 */
public class AddListFailException extends ParseException {
    public AddListFailException() {
        super(Messages.ERROR_ADDLIST_NAME_EMPTY);
    }
}
