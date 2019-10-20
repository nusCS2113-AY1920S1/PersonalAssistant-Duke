package duke.exceptions;


/**
 * Represents duke.exceptions specific to Duke.
 */
public class DukeException extends Exception {
    /**
     * Constructor.
     *
     * @param message Error message.
     */
    public DukeException(final String message) {
        super(message);
    }
}
