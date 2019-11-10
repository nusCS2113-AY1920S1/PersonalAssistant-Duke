package ducats;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DucatsLogger {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Helper function used to for Logger setup, namely the creation of the File Handler and its attachment to the
     * Logger.
     */
    public static void setupLogger() {
        LogManager.getLogManager().reset();
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("./logs/DucatsLogger.log");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
            LOGGER.info("File logger successfully initialized");
        } catch (IOException e) {
            LOGGER.severe("Unable to initialize file logger");
        }
    }

    public static void severe(String message) {
        LOGGER.severe(message);
    }

    public static void warning(String message) {
        LOGGER.warning(message);
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void fine(String message) {
        LOGGER.fine(message);
    }

    public static void finer(String message) {
        LOGGER.finer(message);
    }

    public static void finest(String message) {
        LOGGER.finest(message);
    }


}
