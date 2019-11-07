/**
 * This an exception class that handles loading exception when using Cube. Inherits from CubeException.
 *
 * @author tygq13
 */
package cube.util.exception;

import cube.exception.CubeException;

public class CubeUtilException extends CubeException
{
    /**
     * Constructor with one argument.
     * Constructs the exception with message.
     *
     * @param message the message to be printed when exception happens.
     */
    public CubeUtilException(String message) {
        super(message);
    }
}