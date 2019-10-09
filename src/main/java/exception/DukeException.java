package exception;

/**
 * control.Duke Exception class for exceptions unique to control.Duke.
 */
public class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }

    public DukeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
