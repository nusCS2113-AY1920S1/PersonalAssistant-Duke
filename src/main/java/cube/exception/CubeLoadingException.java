/**
 * This an exception class that handles loading exception when using Cube. Inherits from CubeException.
 *
 * @author tygq13
 */
package cube.exception;

import cube.ui.Message;

public class CubeLoadingException extends CubeException
{
    /**
     * Default Constructor.
     */
    public CubeLoadingException(String filePath) {
        super(Message.IO_ERROR + filePath);
    }
}