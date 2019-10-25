package DukeExceptions;

public class DukeInvalidFormatDescription extends DukeException {
    public DukeInvalidFormatDescription(String message) {
        super(message);
    }

    public DukeInvalidFormatDescription(String message, Throwable cause) {
        super(message, cause);
    }
}
