package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteDeleteCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteDeleteCommand.
 */
public class RouteDeleteParser extends CommandParser {
    int index;

    /**
     * Parses user input into route.
     * @param input The User input
     */
    public RouteDeleteParser(String input) throws DukeException {
        this.index = ParserUtil.getIntegerIndexInList(0, 1, input);
    }

    /**
     * Constructs RouteDeleteCommand object.
     * @return RouteDeleteCommand object
     */
    @Override
    public Command parse() throws DukeException {
        return new RouteDeleteCommand(index);
    }
}
