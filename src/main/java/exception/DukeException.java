package exception;

/**
 * Represents the exception object used to handle errors thrown in the main execution.
 * Inherits from the Exception class.
 */
public class DukeException extends Exception {

    public DukeException(String message) {
        super(message);
    }

    public DukeException() {
        super();
    }

    public void showError() {
        System.out.println(this.getMessage());
    }
}
