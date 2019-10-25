package parser;

import command.Command;
import command.FindCommand;
import exception.DukeException;

/**
 * Extract the components required for the find command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class FindParser extends DescriptionParser {
    public FindParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new FindCommand(taskFeatures);
    }
}
