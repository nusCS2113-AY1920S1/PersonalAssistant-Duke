package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteNodeDeleteCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteNodeDeleteCommand.
 */
public class RouteNodeDeleteParser extends CommandParser {

    private int firstIndex;
    private int secondIndex;

    /**
     * Parses user input into parameter for RouteNodeDeleteCommand.
     * @param input The User input
     */
    public RouteNodeDeleteParser(String input) throws ParseException {
        firstIndex = ParserUtil.getIntegerIndexInList(0, 2, input);
        secondIndex = ParserUtil.getIntegerIndexInList(1, 2, input);
    }

    /**
     * Constructs RouteNodeDeleteCommand object.
     * @return RouteNodeDeleteCommand object
     */
    @Override
    public Command parse() {
        return new RouteNodeDeleteCommand(firstIndex, secondIndex);
    }
}
