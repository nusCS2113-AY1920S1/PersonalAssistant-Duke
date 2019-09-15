package exception;

/**
 * Specifies the name of exception the Duke can throw
 * by extending the parent class <code>Exception</code>
 * without adding extra fields and methods.
 */
public class DukeException extends RuntimeException {

    /**
     * Constructs an <code>DukeException</code> object with exception message.
     */
    public DukeException(String message){
        super(message);
    }
}
