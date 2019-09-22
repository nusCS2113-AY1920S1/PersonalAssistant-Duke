package exception;

/**
 * Specifies the name of exception the Duke can throw
 * by extending the parent class {@code Exception}
 * without adding extra fields and methods.
 */
public class DukeException extends RuntimeException {

    /**
     * Constructs an {@code DukeException} object with exception message.
     */
    public DukeException(String message) {
        super(message);
    }
}
