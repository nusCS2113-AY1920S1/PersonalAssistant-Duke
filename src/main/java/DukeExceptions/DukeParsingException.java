package DukeExceptions;

public class DukeParsingException extends DukeException {
    public DukeParsingException(String message) {
        super(message);
    }

    public DukeParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
