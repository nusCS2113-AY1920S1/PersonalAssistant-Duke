package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when API request returns no data.
 */
public class ApiNullRequestException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public ApiNullRequestException() {
        super(Messages.ERROR_API_DATA_NULL);
    }
}
