package DukeExceptions;

public class DukeLoadingErrorException extends DukeException {
    public DukeLoadingErrorException(String message) {
        super(message);
    }

    public DukeLoadingErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
