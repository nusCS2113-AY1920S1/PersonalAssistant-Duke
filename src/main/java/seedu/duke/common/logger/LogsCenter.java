package seedu.duke.common.logger;

import seedu.duke.common.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Configures and manages loggers and handlers, including their logging level
 * Named {@link Logger}s can be obtained from this class<br>
 * These loggers have been configured to output messages to the console and a {@code .log} file by default,
 *   at the {@code INFO} level. A new {@code .log} file with a new numbering will be created after the log
 *   file reaches 5MB big, up to a maximum of 5 files.<br>
 */
public class LogsCenter {

    private static Level currentLogLevel = Level.ALL;
    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);

    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    private static String logFileName = ".\\data\\logs\\emailManager.log";

    /**
     * Initializes with a custom log level (specified in the {@code config} object)
     * Loggers obtained *AFTER* this initialization will have their logging level changed<br>
     * Logging levels for existing loggers will only be updated if the logger with the same name
     * is requested again from the LogsCenter.
     */
    public static void init() {
        logger.info("currentLogLevel: " + currentLogLevel);
        logFileName = getLogFileName();
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
    public static <T> Logger getLogger(Class<T> clazz) {
        if (clazz == null) {
            return Logger.getLogger("");
        }
        return getLogger(clazz.getSimpleName());
    }

    /**
     * Adds the {@code consoleHandler} to the {@code logger}. <br>
     * Creates the {@code consoleHandler} if it is null.
     */
    private static void addConsoleHandler(Logger logger) {
        if (consoleHandler == null) {
            consoleHandler = createConsoleHandler();
        }
        logger.addHandler(consoleHandler);
    }

    /**
     * Remove all the handlers from {@code logger}.
     */
    private static void removeHandlers(Logger logger) {
        Arrays.stream(logger.getHandlers())
                .forEach(logger::removeHandler);
    }

    /**
     * Adds the {@code fileHandler} to the {@code logger}. <br>
     * Creates {@code fileHandler} if it is null.
     */
    private static void addFileHandler(Logger logger) {
        try {
            if (fileHandler == null) {
                fileHandler = createFileHandler();
            }
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.warning("Error adding file handler for logger.");
        }
    }

    /**
     * Creates a {@code FileHandler} for the log file.
     * @throws IOException if there are problems opening the file.
     */
    private static FileHandler createFileHandler() {
        FileHandler fileHandler = null;
        if (logFileName == null) {
            logFileName = getLogFileName();
        }
        try {
            fileHandler = new FileHandler(logFileName);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(currentLogLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileHandler;
    }

    private static ConsoleHandler createConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(currentLogLevel);
        return consoleHandler;
    }

    /**
     * Gets the filepath to save logs data.
     */
    private static String getLogFileName() {
        Path logPath = Storage.prepareLogFolderPath();
        File logDir = new File(logPath.toString());
        if (!(logDir.exists())) {
            logDir.mkdir();
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        Date date = new Date();
        String dateStr = dateFormat.format(date);
        String fileName = logPath + File.separator +  "log" + dateStr +  ".log";
        return fileName;
    }

}