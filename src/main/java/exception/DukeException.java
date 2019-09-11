/**
 * The parent exception class that handles general exceptions in Using Duke.
 *
 * @author tygq13
 */
package exception;

public class DukeException extends Exception
{
    /**
     * Default constructor.
     */
	public DukeException() {
		super();
	}

    /**
     * Constructor with one argument.
     * Constructs the exception with message.
     *
     * @param message the message to be printed when exception happens.
     */
    public DukeException(String message) {
        super(message);
    }
}