package duke.exceptions;

import duke.util.Log4jLogger;

//@@author HUANGXUANKUN

/**
 * An exception class which design for Duke, all the exception message of DukeException should start with "Oops".
 */
public class DukeException extends Exception {
    /**
     * An exception start with "Oops".
     *
     * @param message exception message.
     */
    public DukeException(String message) {
        super("Oops! " + message);
    }

    /**
     * It throws an exception to be recorded into log.
     *
     * @param className the classname where exception is being thrown.
     * @param message   exception message.
     */
    public DukeException(Class className, String message) {
        super("Oops! " + message);
        Log4jLogger.logException(className, message);
    }
}