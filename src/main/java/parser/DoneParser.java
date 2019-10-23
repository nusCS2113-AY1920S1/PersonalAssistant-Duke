package parser;

import command.Command;
import command.DoneCommand;
import exception.DukeException;

/**
 * Extract the components required for the done command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class DoneParser extends IndexParser {

    public DoneParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new DoneCommand(indexOfTask);
    }
}
