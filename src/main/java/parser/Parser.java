package parser;

import command.Command;
import exception.DukeException;
import exception.MyLogger;

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
    MyLogger logger = new MyLogger(this.getClass().getName(), "errors");

    /**
     * contructs a parser object and initializes a logger.
     * 
     * @param userInput input from user
     * @param command   input command type
     */
    public Parser(String userInput, String command) {
        this.userInput = userInput;
        this.command = command;
    }

    public abstract Command parse() throws DukeException;

    String removeCommandInput(String userInput) throws DukeException {
        String taskFeatures;
        try {
            taskFeatures = userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new DukeException(DukeException.emptyUserDescription());
        }
        return taskFeatures;
    }

}
