package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when index query is out of bounds.
 */
public class QueryOutOfBoundsException extends DukeException {
    private String queriedItem;

    /**
     * Constructs the Exception.
     */
    public QueryOutOfBoundsException(String queriedItem) {
        super(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
        this.queriedItem = queriedItem;
    }

    public String getQueriedItem() {
        return queriedItem;
    }
}
