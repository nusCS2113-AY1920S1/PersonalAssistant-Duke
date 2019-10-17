package duke.core;

public class DukeException extends Exception {
    /**
     *  .
     * @param message .
     */
    public DukeException(String message) {
        super("Oops! " + message);
    }
}