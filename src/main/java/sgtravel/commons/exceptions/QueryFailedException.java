package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when the query fails.
 */
public class QueryFailedException extends DukeException {
    private String queriedItem;

    /**
     * Constructs the Exception.
     *
     * @param queriedItem The item that was queried.
     */
    public QueryFailedException(String queriedItem) {
        super(Messages.ERROR_RESOURCE_NOT_FOUND);
        this.queriedItem = queriedItem;
    }

    /**
     * Gets the name of the queried item.
     *
     * @return queriedItem The name of the queried item.
     */
    public String getQueriedItem() {
        return queriedItem;
    }
}
