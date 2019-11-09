package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when no such category can be found.
 */
public class NoSuchCategoryException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public NoSuchCategoryException() {
        super(Messages.ERROR_CATEGORY_NOT_FOUND);
    }
}
