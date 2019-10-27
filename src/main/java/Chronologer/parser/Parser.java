package chronologer.parser;

import chronologer.command.Command;
import chronologer.exception.ChronologerException;

/**
 * Ensures that all the classes of parser type have implementations of the method parse.
 *
 * @author Fauzan
 * @version v1.0
 */
public abstract class Parser {

    String userInput;
    String command;
    String taskFeatures;
    String checkType;

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
            throw new ChronologerException(ChronologerException.emptyUserDescription());
        }
        return taskFeatures;
    }
}
