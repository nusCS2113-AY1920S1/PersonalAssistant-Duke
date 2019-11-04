package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.RestoreVersionCommand;
import chronologer.exception.ChronologerException;

public class RestoreVersionParser extends VersionParser {

    RestoreVersionParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        int choiceOfVersion = extractVersion(taskFeatures);
        return new RestoreVersionCommand(choiceOfVersion);
    }
}