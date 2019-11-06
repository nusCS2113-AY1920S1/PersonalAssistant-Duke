package duke;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

//@@author talesrune-reused
/**
 * A logger which helps to log warning and severe errors.
 */
public class DukeLogger {
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Sets up a logger with a log file and log levels.
     */
    public void setupLogger() {
        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logr.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("dukeLogger.log", true);
            fh.setLevel(Level.FINE);
            logr.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (java.io.IOException e) {
            logr.log(Level.SEVERE, "Duke logger is not working.", e);
        }
    }
}
