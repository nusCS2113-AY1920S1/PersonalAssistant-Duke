package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.CGraphCommand;

/**
 * Parser class to handle information to CGraphCommand.
 */
public class CGraphCommandParser implements ParserInterface<CGraphCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a Lunch object.
     * @param userInput String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     * @throws DukeException when the user input cannot be parsed
     */
    @Override

    public CGraphCommand parse(String userInput) {
        try {
            InputValidator.validate(userInput);
            String[] lineSplit = userInput.split(" ");
            String type = lineSplit[0];
            int month = Integer.parseInt(lineSplit[2]);
            int year = Integer.parseInt(lineSplit[4]);
            return new CGraphCommand(month, year, type);
        } catch (DukeException e) {
            return new CGraphCommand(false, e.getMessage());
        }
    }
}

