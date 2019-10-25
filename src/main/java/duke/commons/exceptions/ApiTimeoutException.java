package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when API request timeouts.
 */
public class ApiTimeoutException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public ApiTimeoutException() {
        super(Messages.ERROR_API_TIMEOUT);
    }
}
