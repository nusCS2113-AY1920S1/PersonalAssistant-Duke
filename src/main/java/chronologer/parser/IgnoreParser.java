package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.IgnoreCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the ignore command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class IgnoreParser extends IndexParser {

    private Boolean isIgnore;

    IgnoreParser(String userInput, String command, Boolean isIgnore) {
        super(userInput, command);
        this.isIgnore = isIgnore;
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        return new IgnoreCommand(indexOfTask, isIgnore);
    }

}
