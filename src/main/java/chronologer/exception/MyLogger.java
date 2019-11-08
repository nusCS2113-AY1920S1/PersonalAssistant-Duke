package chronologer.exception;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//@@author hanskw4267
/**
 * Handles the creation and writing of log messages for errors.
 * 
 * @author Hans kurnia
 * @version 1.0
 */
public class MyLogger {
    Logger logger;
    FileHandler fh;
    String fileName;
    SimpleFormatter formatter = new SimpleFormatter();

    /**
     * initializes a new logger.
     * 
     * @param name     name of new logger
     * @param fileName name of generated log file (without extention)
     */
    public MyLogger(String name, String fileName) {
        this.fileName = fileName;
        LogManager logManager = LogManager.getLogManager();
        logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        logManager.addLogger(logger);
        fh = null;
    }

    /**
     * writes to error log file.
     * 
     * @param msg      error exceptions message to be logged
     * @param location class where error has occured
     * @param input    the full user input
     */
    public void writeLog(String msg, String location, String input) {
        try {
            fh = new FileHandler(System.getProperty("user.dir") + "/src/ChronologerDatabase/" + fileName + ".log",
                    true);
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.warning(msg + "\nError from: " + location + "\nUser input was: \"" + input + "\"");
            fh.flush();
            fh.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes to error log file.
     * 
     * @param msg      error exceptions message to be logged
     * @param location class where error has occured
     */
    public void writeLog(String msg, String location) {
        try {
            fh = new FileHandler(System.getProperty("user.dir") + "/src/ChronologerDatabase/" + fileName + ".log",
                    true);
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.warning(msg + "\nError from: " + location);
            fh.flush();
            fh.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}