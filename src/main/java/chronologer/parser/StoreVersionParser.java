package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.StoreVersionCommand;
import chronologer.exception.ChronologerException;

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
