package duke.dukeexception;

//@@author talesrune-reused
/**
 * Represents an error occurred when duke is performing an action.
 */
public class DukeException extends Exception {
    /**
     * Creates an exception with the specified message.
     *
     * @param message The error message to be shown.
     */
    public DukeException(String message) {
        super(message);
    }
}
