package parser;

import command.Command;
import command.DeleteCommand;
import exception.DukeException;

/**
 * Extract the components required for the delete command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class DeleteParser extends IndexParser {

    public DeleteParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new DeleteCommand(indexOfTask);
    }

}
