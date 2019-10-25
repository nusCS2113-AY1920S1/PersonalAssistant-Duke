package duke.commons.exceptions;

/**
 * Displays an error when a field is left empty.
 */
public class DukeEmptyFieldException extends DukeException {
    public DukeEmptyFieldException(String message) {
        super(message);
    }
}
