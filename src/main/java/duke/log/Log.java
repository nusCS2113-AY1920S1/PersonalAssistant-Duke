package duke.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Wrapper for the application-wide logger.
 */
public class Log {
    private static final String LOG_FILE_NAME = "spongeBob.log";
    private static Logger logger;
    private static final String ERROR_MESSAGE = " Unable to open log file";
    private static final String LOGGER_NAME = "SpongeBob";

    /**
     * Returns the logger to use, creating one if necessary.
     * @return the logger
     */
    public static Logger getLogger() {
        if (logger != null) {
            return logger;
        }

        logger = Logger.getLogger(LOGGER_NAME);
        logger.setUseParentHandlers(false);

        try {
            FileHandler logFileHandler = new FileHandler(LOG_FILE_NAME);
            logFileHandler.setFormatter(new SimpleFormatter());
            logFileHandler.setLevel(Level.INFO);
            logger.addHandler(logFileHandler);
        } catch (IOException e) {
            logger.warning(ERROR_MESSAGE);
        }

        return logger;
    }
}

