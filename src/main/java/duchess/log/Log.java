package duchess.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Wrapper for the application-wide logger.
 */
public class Log {
    private static final String LOG_FILE_NAME = "duchess.log";
    private static final String LOG_ERROR_MSG = "Unable to open log file.";
    private static Logger logger;

    /**
     * Returns the logger to use, creating one if necessary.
     * @return the logger
     */
    public static Logger getLogger() {
        if (logger != null) {
            return logger;
        }

        logger = Logger.getLogger("Duchess");
        logger.setUseParentHandlers(false);

        try {
            FileHandler logFileHandler = new FileHandler(LOG_FILE_NAME);
            logFileHandler.setFormatter(new SimpleFormatter());
            logFileHandler.setLevel(Level.INFO);
            logger.addHandler(logFileHandler);
        } catch (IOException e) {
            logger.warning(LOG_ERROR_MSG);
        }

        return logger;
    }
}
