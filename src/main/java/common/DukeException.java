package common;

/**
 * A class for exceptions specific to Duke.
 */

public class DukeException extends Exception {
    /**
     * Creates an instance of an exception specific to duke.
     * @param message Message describing the exception thrown.
     */
    public DukeException(String message) {
        super(message);
    }
}
