package duke.exception;

/**
 * The exception Duke throws upon encountering an unexpected error not caused by the user nor
 * by invalid validation of parameters. 
 */
public class DukeRuntimeException extends RuntimeException {
    /**
     * Constructs an {@code DukeRuntimeException} object with exception message.
     */
    public DukeRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code DukeRuntimeException} object with exception message and cause.
     */
    public DukeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
