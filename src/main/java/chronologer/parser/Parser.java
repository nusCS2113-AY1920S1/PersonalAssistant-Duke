package chronologer.parser;

import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.exception.MyLogger;
import chronologer.ui.UiTemporary;

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
    boolean hasModCode;
    MyLogger logger = new MyLogger(this.getClass().getName(), "ParserErrors");

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

    public abstract Command parse() throws ChronologerException;

    String removeCommandInput(String userInput) throws ChronologerException {
        String taskFeatures;
        try {
            taskFeatures = userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            UiTemporary.printOutput(ChronologerException.emptyUserDescription());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyUserDescription());
        }
        return taskFeatures;
    }

}
