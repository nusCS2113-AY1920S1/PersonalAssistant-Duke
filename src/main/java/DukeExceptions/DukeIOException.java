package DukeExceptions;

public class DukeIOException extends DukeException {
    public DukeIOException(String message) {
        super(message);
    }

    public DukeIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
