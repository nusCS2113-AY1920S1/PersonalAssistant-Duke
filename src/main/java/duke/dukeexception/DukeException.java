package duke.dukeexception;

//@@author talesrune-reused
//Reused from https://github.com/Jefferson111/main/blob/master/src/main/java/duke/commons/DukeException.java
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
