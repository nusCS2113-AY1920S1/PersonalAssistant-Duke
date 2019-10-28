package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.SearchCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the search command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class SearchParser extends IndexParser {

    public SearchParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        Long duration;
        duration = (long) indexOfTask;
        return new SearchCommand(duration);
    }
}
