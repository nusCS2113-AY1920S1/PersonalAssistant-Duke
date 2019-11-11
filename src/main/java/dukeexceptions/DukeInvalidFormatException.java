package dukeexceptions;

/**
 * Represents the exception specifically catered to
 * wrong command format input by user.
 */
public class DukeInvalidFormatException extends Exception {
    public DukeInvalidFormatException(String message) {
        super(message);
    }

}
