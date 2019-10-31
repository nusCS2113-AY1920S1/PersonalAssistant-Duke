package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteEditCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteEditCommand.
 */
public class RouteEditParser extends CommandParser {
    private int firstIndex;
    private String firstEventIndex;
    private String secondEventIndex;

    /**
     * Parses user input into parameter for RouteEditCommand.
     * @param input The User input
     */
    public RouteEditParser(String input) throws ParseException {
        this.firstIndex = ParserUtil.getIntegerIndexInList(0, 3, input);
        this.firstEventIndex = ParserUtil.getFieldInList(1, 3, input);
        this.secondEventIndex = ParserUtil.getFieldInList(2, 3, input);
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
