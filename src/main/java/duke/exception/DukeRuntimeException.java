package duke.exception;

/**
 * The exception Duke throws upon encountering an unexpected error not caused by the user nor
 * by invalid validation of parameters.
 */
public class DukeRuntimeException extends RuntimeException {
    private static final String MESSAGE_FATAL_ERROR = "A fatal error has occurred. %s.";

    /**
     * Constructs an {@code DukeRuntimeException} object with exception message.
     */
    public DukeRuntimeException(String message) {
        super(String.format(MESSAGE_FATAL_ERROR, message));
    }

    /**
     * Constructs an {@code DukeRuntimeException} object with exception message and cause.
     */
    public DukeRuntimeException(String message, Throwable cause) {
        super(String.format(MESSAGE_FATAL_ERROR, message), cause);
    }
}
