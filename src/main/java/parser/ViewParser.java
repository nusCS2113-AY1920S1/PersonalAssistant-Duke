package parser;

import command.Command;
import command.ViewCommand;
import exception.DukeException;

/**
 * Extract the components required for the view command.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class ViewParser extends DescriptionParser {

    public ViewParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        return new ViewCommand(taskFeatures);
    }

}
