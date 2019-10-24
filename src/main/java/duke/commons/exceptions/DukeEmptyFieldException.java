package duke.commons.exceptions;

/**
 * Represents an empty field command error.
 */
public class DukeEmptyFieldException extends DukeException {
    public DukeEmptyFieldException(String message) {
        super(message);
    }
}
