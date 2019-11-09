package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.ViewCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the view command.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class ViewParser extends DescriptionParser {

    ViewParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        return new ViewCommand(taskFeatures);
    }

}
