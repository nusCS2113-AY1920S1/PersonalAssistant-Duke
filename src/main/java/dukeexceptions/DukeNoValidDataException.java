package dukeexceptions;

/**
 * Represents the exception specifically catered to
 * no valid data found to support command.
 */
public class DukeNoValidDataException extends Exception {
    public DukeNoValidDataException(String message) {
        super(message);
    }
}
