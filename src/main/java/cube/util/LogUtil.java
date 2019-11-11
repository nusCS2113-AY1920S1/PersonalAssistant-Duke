/**
 * Handles and configures loggers and handlers.
 * Default logger is configured to 1 log file, 10MB in size, Level.INFO.
 * Design has been referenced from Address Book (Level 3).
 * https://github.com/se-edu/addressbook-level3
 */

package cube.util;

import cube.storage.config.LogConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogUtil {
    private static int maxFileCount = 1;
    private static int maxFileSizeBytes = (int)(Math.pow(2, 20) * 10); // 10MB;
    private static String logFileName = "cube.log";
    private static Level currentLogLevel = Level.INFO;
    private static Logger logger = LogUtil.getLogger(LogUtil.class);
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    /**
     * Initializes the logger with custom configurations from the LogConfig object.
     * @param logConfig Object containing the log configurations to load form.
     */
    public static void init(LogConfig logConfig) {
        logger.info("Initializing logger with stored configurations.");
        maxFileCount = logConfig.getMaxFileCount();
        maxFileSizeBytes = logConfig.getMaxFileSizeBytes();
        currentLogLevel = Level.parse(logConfig.getCurrentLogLevel());
        logger = LogUtil.getLogger(LogUtil.class);
        logger.info("Configured Log Level: " + currentLogLevel);
    }

    /**
     * Creates a logger with the given name.
     */
    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        removeHandlers(logger);
        addConsoleHandler(logger);
        addFileHandler(logger);

        return Logger.getLogger(name);
    }

    /**
     * Creates a Logger for the given class name.
     */
    public static <T> Logger getLogger(Class<T> classType) {
        if (classType == null) {
            return Logger.getLogger("");
        }
        return getLogger(classType.getSimpleName());
    }

    /**
     * Adds the ConsoleHandler to the logger.
     * Creates the ConsoleHandler if it is null.
     */
    private static void addConsoleHandler(Logger logger) {
        if (consoleHandler == null) {
            consoleHandler = createConsoleHandler();
        }
        logger.addHandler(consoleHandler);
    }

    /**
     * Remove all the handlers from logger.
     */
    private static void removeHandlers(Logger logger) {
        Arrays.stream(logger.getHandlers()).forEach(logger::removeHandler);
    }

    /**
     * Adds the FileHandler to the logger.
     * Creates the FileHandler if it is null.
     */
    private static void addFileHandler(Logger logger) {
        try {
            if (fileHandler == null) {
                fileHandler = createFileHandler();
            }
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.warning("Error adding file handler for logger.");
        }
    }

    /**
     * Creates a FileHandler for the log file.
     * Outputs the logs into a log file.
     * @throws IOException if there are problems opening the file.
     */
    private static FileHandler createFileHandler() throws IOException {
        FileHandler fileHandler = new FileHandler(logFileName, maxFileSizeBytes, maxFileCount, true);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(currentLogLevel);
        return fileHandler;
    }

    /**
     * Creates a ConsoleHandler for the log file.
     * Outputs the logs into the console.
     */
    private static ConsoleHandler createConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(currentLogLevel);
        return consoleHandler;
    }
}