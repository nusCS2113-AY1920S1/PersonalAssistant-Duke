package dukeexceptions;

/**
 * Represents the exception specifically catered to
 * invalid command input by user.
 */
public class DukeInvalidCommandException extends Exception {
    public DukeInvalidCommandException(String message) {
        super(message);
    }
}
