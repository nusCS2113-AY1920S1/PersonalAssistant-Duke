package duke.commons.exceptions;

import duke.commons.Messages;

public class CategoryNotFoundException extends DukeException {
    public CategoryNotFoundException() {
        super(Messages.ERROR_CATEGORY_NOT_FOUND);
    }
}
