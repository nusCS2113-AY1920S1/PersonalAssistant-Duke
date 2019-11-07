package owlmoney.commons.log;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogsCenter {
    private static final int MAX_FILE_COUNT = 5;
    private static final int MAX_FILE_SIZE_IN_BYTES = (int) (Math.pow(2, 20) * 5); // 5MB
    private static final String LOG_PATH = "data/logs/";
    private static final String LOG_FILE = LOG_PATH + "application.log";
    private static Level currentLogLevel = Level.INFO;
    private static Level currentConsoleLogLevel = Level.SEVERE;
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    /**
     * Creates a logger with the given name.
     */
    public static Logger getLogger(String name) {
        requireNonNull(name);

        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        removeHandlers(logger);
        addConsoleHandler(logger);
        addFileHandler(logger);

        return Logger.getLogger(name);
    }

    /**
     * Creates a Logger for the given class that is being passed as a parameter to this method.
     *
     * @param generalClass Class of any type that requires logging.
     * @param <T> Generic type of value being boxed.
     * @return Result of creation of a Logger for the given class name.
     */
    public static <T> Logger getLogger(Class<T> generalClass) {
        requireNonNull(generalClass);

        if (generalClass == null) {
            return Logger.getLogger("");
        }
        return getLogger(generalClass.getSimpleName());
    }

    /**
     * Adds a console handler and set appropriate console logging levels or create a console handler if null.
     *
     * @param logger An instance of a Logger object.
     */
    private static void addConsoleHandler(Logger logger) {
        assert logger != null;

        if (consoleHandler == null) {
            consoleHandler = createConsoleHandler();
        }
        logger.addHandler(consoleHandler);
    }

    /**
     * Removes handlers from Logger.
     *
     * @param logger An instance of a Logger object.
     */
    private static void removeHandlers(Logger logger) {
        assert logger != null;

        Arrays.stream(logger.getHandlers())
                .forEach(logger::removeHandler);
    }

    /**
     * Adds a file handler and set appropriate file logging levels or create a file handler if null.
     *
     * @param logger An instance of a Logger object.
     */
    private static void addFileHandler(Logger logger) {
        assert logger != null;

        try {
            if (fileHandler == null) {
                fileHandler = createFileHandler();
            }
            logger.addHandler(fileHandler);
        } catch (IOException exceptionMessage) {
            logger.warning("Error adding file handler for logger.");
        }
    }

    /**
     * Creates a file handler for the log file and set the appropriate logging levels.
     * @return fileHandler In charge of logging to files.
     * @throws IOException If there are errors adding creating or opening the file handler.
     */
    private static FileHandler createFileHandler() throws IOException {
        File dir = new File(LOG_PATH);
        if (!dir.exists()) {
            boolean result = dir.mkdirs();
        }
        FileHandler fileHandler = new FileHandler(LOG_FILE, MAX_FILE_SIZE_IN_BYTES,
                MAX_FILE_COUNT, true);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(currentLogLevel);
        return fileHandler;
    }

    /**
     * Creates a console handler for logging and set the appropriate logging levels.
     *
     * @return consoleHandler In charge of logging to console.
     */
    private static ConsoleHandler createConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(currentConsoleLogLevel);
        return consoleHandler;
    }
}