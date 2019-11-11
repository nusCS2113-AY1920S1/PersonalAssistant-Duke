package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.StoreVersionCommand;
import chronologer.exception.ChronologerException;

/**
 * Extracts the version the user wants.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class StoreVersionParser extends VersionParser {

    StoreVersionParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        int choiceOfVersion = extractVersion(taskFeatures);
        return new StoreVersionCommand(choiceOfVersion);
    }
}
