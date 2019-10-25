package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when API request fails.
 */
public class ApiFailedRequestException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public ApiFailedRequestException() {
        super(Messages.ERROR_API_REQUEST_FAILED);
    }
}
