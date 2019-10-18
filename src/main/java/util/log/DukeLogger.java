package util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DukeLogger {
    private static Logger _logger;

    public static void logDebug(Class className, String message) {
        _logger = LogManager.getLogger(className);
        _logger.debug(message);
    }

    public static void logInfo(Class className, String message) {
        _logger = LogManager.getLogger(className);
        _logger.info(message);
    }

    public static void logError(Class className, String message) {
        _logger = LogManager.getLogger(className);
        _logger.error(message);
    }
}
