package duke.exception;

/**
 * Class that handles custom exceptions throw by method calls and classes.
 */
public class DukeException extends Exception {
    private String message;

    public DukeException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Converts any exception messages to string format.
     * @return The corresponding message in string format.
     */
    public String toString() {
        return this.message;
    }
}
