package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteListCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteListCommand.
 */
public class RouteListParser extends CommandParser {

    private int index;

    /**
     * Parses user input into index.
     * @param input The User input
     */
    public RouteListParser(String input) throws ParseException {
        index = ParserUtil.getIntegerIndexInList(0, 1, input);
    }

    /**
     * Constructs RouteListCommand object.
     * @return RouteListCommand object
     */
    @Override
    public Command parse() {
        return new RouteListCommand(index);
    }
}
