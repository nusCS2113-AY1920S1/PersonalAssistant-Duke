package common;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class LoggerController {
    //@@author JustinChia1997
    private static Logger logger;
    private static final String debugString = "DEBUG_MESSAGE: ";

    /**
     * Info messages are unimportant and just to detail the running of the program
     */
    public static void logInfo(Class className, String message) {
        logger = getLogger(className.getName());
        logger.log(Level.INFO, message);
    }

    /**
     * Debug messages help create breakpoints to identify errors
     */
    public static void logDebug(Class className, String message) {
        logger = getLogger(className.getName());
        logger.log(Level.INFO, debugString + message);
    }

    /**
     * Error logging helps us view if the program is running smoothly
     */
    public static void logWarning(Class className, String message) {
        logger = getLogger(className.getName());
        logger.log(Level.WARNING, message);
    }


}
