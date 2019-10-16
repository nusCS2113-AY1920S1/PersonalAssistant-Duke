package duke.exception;

/**
 * Represents an {@link Exception} class used to throw exceptions from the Duke class.
 */
public class DukeException extends Exception {

    public DukeException(String message) {
        super("\t ☹ OOPS!!! " + message);
    }
}
