package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.CGraphCommand;

//@@author koushireo

/**
 * Parser class to handle information to CGraphCommand.
 */
public class CGraphCommandParser implements ParserInterface<CGraphCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a Lunch object.
     * @param userInputStr String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     * @throws ProgramException when the user input cannot be parsed
     */
    @Override

    public CGraphCommand parse(String userInputStr) {
        int month = 0;
        try {
            InputValidator.validate(userInputStr);
        } catch (ProgramException e) {
            return new CGraphCommand(false, e.getMessage());
        }
        String[] lineSplit = ArgumentSplitter.splitArguments(userInputStr," ");
        try {
            month = Integer.parseInt(lineSplit[2]);
        } catch (NumberFormatException e) {
            return new CGraphCommand(false, "Please input a valid number for month");
        }
        if (month < 1 || month > 12) {
            return new CGraphCommand(false, "Month can only be between 1 to 12");
        }
        int year = 0;
        try {
            year = Integer.parseInt(lineSplit[4]);
        } catch (NumberFormatException e) {
            return new CGraphCommand(false, "Please input a valid number for year");
        }
        String type = lineSplit[0];
        return new CGraphCommand(month, year, type);
    }
}

