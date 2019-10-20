/**
 * This an exception class that handles loading exception when using Duke. Inherits from DukeException.
 *
 * @author tygq13
 */
package cube.exception;

import cube.ui.Message;

public class DukeLoadingException extends DukeException
{
    /**
     * Default Constructor.
     */
    public DukeLoadingException(String type, String filePath) {
        if (type.equals("ClassNotFound")) {
            super(Message.IO_Class_Not_Found_ERROR + filePath);
        } else {
            //type.equals "IOException"
            super(Message.IO_ERROR + filePath);
        }
    }
}