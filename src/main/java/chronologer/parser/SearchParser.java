//@@author hanskw4267
package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.SearchCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the search command from the user input.
 *
 * @author Hans kurnia
 * @version v2.0
 */
public class SearchParser extends DescriptionParser {

    public SearchParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        Long duration;
        try {
            duration = Long.parseLong(taskDescription);
        } catch (NumberFormatException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.invalidDuration());
        }
        return new SearchCommand(duration);
    }
}
