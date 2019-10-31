package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteNodeListCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteNodeListCommand.
 */
public class RouteNodeListParser extends CommandParser {

    private int firstIndex;
    private int secondIndex;

    /**
     * Parses user input into parameter for RouteNodeListCommand.
     * @param input The User input
     */
    public RouteNodeListParser(String input) throws ParseException {
        firstIndex = ParserUtil.getIntegerIndexInList(0, 2, input);
        secondIndex = ParserUtil.getIntegerIndexInList(1, 2, input);
    }

    /**
     * Constructs RouteNodeListCommand object.
     * @return RouteNodeListCommand object
     */
    @Override
    public Command parse() {
        return new RouteNodeListCommand(firstIndex, secondIndex);
    }
}
