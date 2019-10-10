package duke.exception;

/**
 * Base class for all Duke-specific exceptions.
 */
public class DukeException extends Exception {

    /**
     * Format a custom message from the process which threw the exception, and create an Exception object with that
     * message.
     *
     * @param msg String describing the cause of the exception, and what the user can do to fix it.
     */
    public DukeException(String msg) {
        super("Oops! :( " + msg); //yeah this is pretty useless for now
    }
}
