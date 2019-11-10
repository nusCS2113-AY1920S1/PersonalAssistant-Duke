package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.DeleteCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the delete command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class DeleteParser extends IndexParser {

    DeleteParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        return new DeleteCommand(indexOfTask);
    }

}
