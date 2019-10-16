package exception;

/**
 * Represents the exception object used to handle errors thrown in the main execution.
 * Inherits from the Exception class.
 */
public class WordUpException extends Exception {

    public WordUpException(String message) {
        super(message);
    }

    public WordUpException() {
        super();
    }

    public String showError() {
        return this.getMessage();
    }
}
