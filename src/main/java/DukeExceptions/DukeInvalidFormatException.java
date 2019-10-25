package DukeExceptions;

public class DukeInvalidFormatException extends DukeException {
    public DukeInvalidFormatException(String message) {
        super(message);
    }

    public DukeInvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
