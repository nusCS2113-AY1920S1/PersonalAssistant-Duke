package DukeExceptions;

public class DukeInvalidDateTimeException extends DukeException {
    public DukeInvalidDateTimeException(String message) {
        super(message);
    }

    public DukeInvalidDateTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
