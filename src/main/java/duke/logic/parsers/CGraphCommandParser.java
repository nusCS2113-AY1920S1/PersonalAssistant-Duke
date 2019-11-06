package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.CGraphCommand;

//@@author koushireo

/**
 * Parser class to handle information to CGraphCommand.
 */
public class CGraphCommandParser implements ParserInterface<CGraphCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a Lunch object.
     * @param userInputStr String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     * @throws DukeException when the user input cannot be parsed
     */
    @Override

    public CGraphCommand parse(String userInputStr) {
        int month = 0;
        int year = 0;
        try {
            InputValidator.validate(userInputStr);
        } catch (DukeException e) {
            return new CGraphCommand(false, e.getMessage());
        }
        String[] lineSplit = userInputStr.split(" ");
        String type = lineSplit[0];
        try {
            month = Integer.parseInt(lineSplit[2]);
        } catch (NumberFormatException e) {
            return new CGraphCommand(false, "Please input a valid number for month");
        }
        if (month < 1 || month > 12) {
            return new CGraphCommand(false, "Month can only be between 1 to 12");
        }
        try {
            month = Integer.parseInt(lineSplit[4]);
        } catch (NumberFormatException e) {
            return new CGraphCommand(false, "Please input a valid number for year");
        }
        return new CGraphCommand(month, year, type);
    }
}

