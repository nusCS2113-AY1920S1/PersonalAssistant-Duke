/**
 * This an exception class that handles loading exception when using Duke. Inherits from DukeException.
 *
 * @author tygq13
 */
package exception;

import ui.Message;

public class DukeLoadingException extends DukeException
{
    /**
     * Default Constructor.
     */
    public DukeLoadingException(String filePath) {
        super(Message.IO_ERROR + filePath);
    }
}