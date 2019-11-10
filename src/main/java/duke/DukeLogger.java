package duke;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

//@@author talesrune-reused
//Reused from https://github.com/simply-coded/java-tutorials/blob/master/code/tutorial_48.java with minor modifications
/**
 * A logger which helps to log warning and severe errors.
 */
public class DukeLogger {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Sets up a logger with a log file and log levels.
     */
    public void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.SEVERE);
        logger.addHandler(consoleHandler);

        try {
            FileHandler fileHandler = new FileHandler("dukeLogger.log", true);
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "Duke logger is not working.", e);
        }
    }
}
