package util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArchDukeLogger {
    private static Logger logger;

    public static void logDebug(String className, String message) {
        logger = LogManager.getLogger(className);
        logger.debug(message);
    }

    public static void logInfo(String className, String message) {
        logger = LogManager.getLogger(className);
        logger.info(message);
    }

    public static void logError(String className, String message) {
        logger = LogManager.getLogger(className);
        logger.error(message);
    }
}
