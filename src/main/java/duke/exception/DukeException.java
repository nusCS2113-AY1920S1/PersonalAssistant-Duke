package duke.exception;

/**
 * Specifies the name of duke.exception the duke.Duke can throw
 * by extending the parent class {@code Exception}
 * without adding extra fields and methods.
 */
public class DukeException extends RuntimeException {

    /**
     * Constructs an {@code DukeException} object with duke.exception message.
     */
    public DukeException(String message) {
        super(message);
    }
}
