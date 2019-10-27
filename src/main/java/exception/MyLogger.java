package exception;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    Logger logger;
    FileHandler fh;
    String fileName;

    /**
     * initializes a new logger.
     * 
     * @param name name of new logger
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
            FileHandler fh = new FileHandler(System.getProperty("user.dir") + "/src/DukeDatabase/" + fileName + ".log",
                    true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.warning(msg + "\nError from: " + location + "\nUser input was: \"" + input + "\"");
            fh.flush();
            fh.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}