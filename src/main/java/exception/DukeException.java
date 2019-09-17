package exception;

/**
 * Duke Exception class for exceptions unique to Duke.
 */
public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }

    public DukeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
