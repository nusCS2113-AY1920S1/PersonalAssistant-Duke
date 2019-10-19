package duke.commons.exceptions;

/**
 * DukeException is an exception class that extends from the class Exception.
 */
public class DukeException extends Exception {
    /**
     * This is the constructor of DukeException.
     * @param message this is the error message generated from a DukeException
     */
    public DukeException(String message) {
        super(message);
    }
}