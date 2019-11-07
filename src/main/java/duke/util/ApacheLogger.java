package duke.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Logger allows for the logging of errors that occur.
 * Messages logged can be saved to be viewed at a later time.
 */
public class ApacheLogger {
    private static Logger logger;

    /**
     * Method will create a log about the error.
     *
     * @param modelName The class that caused the error
     * @param msg       The message of the error
     */
    public static void logError(final String modelName, final String msg) {
        logger = LogManager.getLogger(modelName);
        logger.debug(msg);
    }
}
