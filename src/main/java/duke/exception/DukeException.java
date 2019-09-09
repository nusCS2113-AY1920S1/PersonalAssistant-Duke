package duke.exception;

/**
 * Represents a newly specified <code>Exception</code>.
 */
public class DukeException extends Exception {
    /**
     * Constructor for <code>DukeException</code>.
     * @param error Error message.
     */
    public DukeException(String error) {
        super(error);
    }
}