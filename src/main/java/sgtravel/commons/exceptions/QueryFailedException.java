package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when index query is out of bounds.
 */
public class QueryFailedException extends DukeException {
    private String queriedItem;

    /**
     * Constructs the Exception.
     */
    public QueryFailedException(String queriedItem) {
        super(Messages.ERROR_RESOURCE_NOT_FOUND);
        this.queriedItem = queriedItem;
    }

    public String getQueriedItem() {
        return queriedItem;
    }
}
