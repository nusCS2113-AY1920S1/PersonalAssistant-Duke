package dukeexceptions;

/**
 * Represents the exception specifically catered to
 * invalid date and time input by user.
 */
public class DukeInvalidDateTimeException extends Exception {
    public DukeInvalidDateTimeException(String message) {
        super(message);
    }
}
