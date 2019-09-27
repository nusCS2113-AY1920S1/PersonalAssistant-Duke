package duke.exception;

/**
 * Represents a newly specified Exception.
 */
public class DukeException extends Exception {

    /**
     * Constructor for DukeException.
     *
     * @param error Error message.
     */
    public DukeException(String error) {
        super(error);
    }
}