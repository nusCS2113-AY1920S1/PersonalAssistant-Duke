/**
 * This an exception class that handles loading exception when using Cube. Inherits from CubeException.
 *
 * @author tygq13
 */
package cube.exception;

public class CubeLoadingException extends CubeException
{
    /**
     * Default Constructor.
     */
    public CubeLoadingException(String filePath) {
        super(ErrorMessage.IO_ERROR + filePath);
    }
}