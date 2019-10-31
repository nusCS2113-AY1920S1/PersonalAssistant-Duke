package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.FreeTimeCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for FreeTimeCommand.
 */
public class FreeTimeParser extends CommandParser {

    private int index;

    /**
     * Parses user input into index.
     * @param input The User input
     */
    public FreeTimeParser(String input) throws ParseException {
        index = ParserUtil.getIntegerIndexInList(0, 1, input);
    }

    /**
     * Constructs FreeTimeCommand object.
     * @return FreeTimeCommand object
     */
    @Override
    public Command parse() {
        return new FreeTimeCommand(index);
    }
}
