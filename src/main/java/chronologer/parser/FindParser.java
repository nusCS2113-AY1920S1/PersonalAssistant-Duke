package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.FindCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the find command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class FindParser extends DescriptionParser {
    FindParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        return new FindCommand(taskFeatures);
    }
}
