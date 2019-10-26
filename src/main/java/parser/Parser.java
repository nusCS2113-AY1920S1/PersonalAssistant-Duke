package parser;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import command.Command;
import exception.DukeException;

/**
 * Ensures that all the classes of parser type have implementations of the
 * method parse.
 *
 * @author Fauzan
 * @version v1.0
 */
public abstract class Parser {

    String userInput;
    String command;
    String taskFeatures;
    String checkType;
    Logger logger;
    FileHandler fh;
    
    /**
     * contructs a parser object and initializes a logger.
     * 
     * @param userInput input from user
     * @param command input command type
     */
    public Parser(String userInput, String command) {
        this.userInput = userInput;
        this.command = command;
        LogManager logManager = LogManager.getLogManager();
        logger = Logger.getLogger(Parser.class.getName());
        logger.setUseParentHandlers(false);
        logManager.addLogger(logger);
        fh = null;
    }

    public abstract Command parse() throws DukeException;

    String removeCommandInput(String userInput) throws DukeException {
        String taskFeatures;
        try {
            taskFeatures = userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new DukeException(DukeException.emptyUserDescription());
        }
        return taskFeatures;
    }

    /**
     * writes to error log file.
     * 
     * @param msg error exceptions message to be logged
     * @param location class where error has occured
     * @param input the full user input
     */
    protected void writeLog(String msg, String location, String input) {
        try {
            FileHandler fh = new FileHandler(System.getProperty("user.dir") + "/src/DukeDatabase/errors.log",true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.warning(msg + "\nError from " + location + "\nUser input was \"" + input + "\"");
            fh.flush();
            fh.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
