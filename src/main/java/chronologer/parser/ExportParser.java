package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.ExportCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the export command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class ExportParser extends DescriptionParser {

    ExportParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        return new ExportCommand(taskFeatures);
    }
}
