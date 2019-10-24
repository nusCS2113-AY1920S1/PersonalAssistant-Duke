package duke.commons.exceptions;

/**
 * Displays an error when an API call fails.
 */
public class DukeApiException extends DukeException {
    public DukeApiException(String message) {
        super(message);
    }
}
