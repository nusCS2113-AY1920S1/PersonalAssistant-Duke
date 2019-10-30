package duke.exceptions;

import duke.util.Log4jLogger;

public class DukeException extends Exception {
    /**
     *  .
     * @param message .
     */
    public DukeException(String message) {
        super("Oops! " + message);
    }

    public DukeException(Class className, String message) {
        super("Oops! " + message);
        Log4jLogger.logException(className,message);
    }
}