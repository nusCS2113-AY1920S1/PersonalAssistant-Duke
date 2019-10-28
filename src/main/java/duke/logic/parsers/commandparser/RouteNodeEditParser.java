package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
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
    public RouteNodeEditParser(String input) throws DukeException {
        firstIndex = ParserUtil.getFirstIndex(input);
        secondIndex = ParserUtil.getSecondIndex(input);
        thirdFieldInList = ParserUtil.getFieldInList(3, 4, getWord(input));
        fourthFieldInList = ParserUtil.getFieldInList(4, 4, getWord(input));
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
