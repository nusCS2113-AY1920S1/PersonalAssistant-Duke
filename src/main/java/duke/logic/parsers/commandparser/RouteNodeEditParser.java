package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteNodeEditCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteNodeEditCommand.
 */
public class RouteNodeEditParser extends CommandParser {
    private final int firstIndex;
    private final int secondIndex;
    private final String thirdFieldInList;
    private final String fourthFieldInList;

    /**
     * Parses user input into parameter for RouteNodeEditCommand.
     * @param input The User input
     */
    public RouteNodeEditParser(String input) throws ParseException {
        firstIndex = ParserUtil.getIntegerIndexInList(0, 3, input);
        secondIndex = ParserUtil.getIntegerIndexInList(1, 3, input);
        thirdFieldInList = ParserUtil.getFieldInList(2, 4, input);
        fourthFieldInList = ParserUtil.getFieldInList(3, 4, input);
    }

    /**
     * Constructs RouteNodeEditCommand object.
     * @return RouteNodeEditCommand object
     */
    @Override
    public Command parse() {
        return new RouteNodeEditCommand(firstIndex, secondIndex, thirdFieldInList, fourthFieldInList);
    }
}
