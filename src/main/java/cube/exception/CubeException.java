/**
 * The parent exception class that handles general exceptions in Using Duke.
 *
 * @author tygq13
 */
package cube.exception;

public class CubeException extends Exception
{
    /**
     * Default constructor.
     */
	public CubeException() {
		super();
	}

    /**
     * Constructor with one argument.
     * Constructs the exception with message.
     *
     * @param message the message to be printed when exception happens.
     */
    public CubeException(String message) {
        super(message);
    }
}