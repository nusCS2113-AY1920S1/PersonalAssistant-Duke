package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteEditCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteEditCommand.
 */
public class RouteEditParser extends CommandParser {
    int firstIndex;
    String firstEventIndex;
    String secondEventIndex;

    /**
     * Parses user input into parameter for RouteEditCommand.
     * @param input The User input
     */
    public RouteEditParser(String input) throws DukeException {
        this.firstIndex = ParserUtil.getFirstIndex(input);
        this.firstEventIndex = getEventIndexInList(1, input);
        this.secondEventIndex = getEventIndexInList(0, input);
    }

    /**
     * Constructs RouteEditCommand object.
     * @return RouteEditCommand object
     */
    @Override
    public Command parse() {
        return new RouteEditCommand(firstIndex, firstEventIndex, secondEventIndex);
    }
}
