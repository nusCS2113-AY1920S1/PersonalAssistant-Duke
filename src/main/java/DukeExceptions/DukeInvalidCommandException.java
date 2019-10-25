package DukeExceptions;

public class DukeInvalidCommandException extends DukeException{

    public DukeInvalidCommandException(String message) {
        super(message);
    }

    public DukeInvalidCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
