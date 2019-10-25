package parser;

import command.Command;
import exception.DukeException;

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

    public abstract Command parse() throws DukeException;

    String removeCommandInput(String userInput) throws DukeException {
        String taskFeatures;
        try {
            taskFeatures = userInput.split("\\s+", 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DukeException.emptyUserDescription());
        }
        return taskFeatures;
    }
}
