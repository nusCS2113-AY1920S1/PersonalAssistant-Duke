package duke.commons.exceptions;

/**
 * Represents an API error.
 */
public class DukeApiException extends DukeException {
    public DukeApiException(String message) {
        super(message);
    }
}
