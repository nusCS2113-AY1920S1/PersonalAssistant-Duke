package parser;

import command.Command;
import command.SearchCommand;
import exception.DukeException;

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
    public Command parse() throws DukeException {
        super.extract();
        Long duration;
        duration = (long) indexOfTask;
        return new SearchCommand(duration);
    }
}
