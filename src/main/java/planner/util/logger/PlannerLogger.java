// @@author namiwa

package planner.util.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import planner.logic.exceptions.legacy.ModException;
import planner.util.logger.formatter.LoggerFormatter;
import planner.util.storage.Storage;

public class PlannerLogger {

    private static FileHandler fileText;
    private static Formatter formatter = new LoggerFormatter();

    private static FileHandler fileHTML;
    private static Formatter formatterHTML;

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup up for our logger file, will continue to add logs after initial run.
     * @throws IOException when log file is failed to be made.
     */
    public static void setLogFile(Storage store) throws IOException {
        Path log = Paths.get("data/logging.log");
        if (!Files.isRegularFile(log)) {
            store.makeFile(log);
        }
        logger.setUseParentHandlers(false);
        fileText = new FileHandler("data/logging.log", 8096, 1, true);
        fileText.setFormatter(formatter);
        logger.setLevel(Level.INFO);
        logger.addHandler(fileText);
        logger.log(Level.INFO, "Log File create: Success");
    }

    /**
     * Overloaded function for logging module exceptions.
     * @param em ModException super class.
     */
    public static void log(ModException em) {
        logger.log(Level.WARNING, " :" + em.toString());
    }

    /**
     * Overloaded function for logging any other exceptions.
     * @param e Exceptions super class.
     */
    public static void log(Exception e) {
        logger.severe(e.toString());
        logger.log(Level.WARNING, " :" + e.toString());
    }

    /**
     * Overloaded function for logging IOException exceptions.
     * @param eio only instance thrown is when log files fails to be made.
     */
    public static void log(IOException eio) {
        logger.severe(eio.toString());
        logger.log(Level.WARNING, " :" + eio.toString());
    }

    public static void log(Throwable e) {
        logger.severe(e.toString());
        logger.log(Level.WARNING, ":" + e.toString());
    }
}
