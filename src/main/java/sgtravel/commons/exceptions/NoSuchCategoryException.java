package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class NoSuchCategoryException extends DukeException {
    public NoSuchCategoryException() {
        super(Messages.ERROR_CATEGORY_NOT_FOUND);
    }
}
