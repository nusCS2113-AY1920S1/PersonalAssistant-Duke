package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.DoneCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the done command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class DoneParser extends IndexParser {

    DoneParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        return new DoneCommand(indexOfTask);
    }
}
