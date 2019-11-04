package duke.logic.parsers.commandparsers;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteNodeEditCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteNodeEditCommand.
 */
public class RouteNodeEditParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    /**
     * Constructs the RouteNodeEditParser.
     */
    public RouteNodeEditParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteNodeEditCommand object.
     * @return RouteNodeEditCommand object.
     * @throws ParseException If RouteNodeEditCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int firstIndex = ParserUtil.getIntegerIndexInList(ZERO, FOUR, input);
        int secondIndex = ParserUtil.getIntegerIndexInList(ONE, FOUR, input);
        String thirdFieldInList = ParserUtil.getFieldInList(TWO, FOUR, input);
        String fourthFieldInList = ParserUtil.getFieldInList(THREE, FOUR, input);
        return new RouteNodeEditCommand(firstIndex, secondIndex, thirdFieldInList, fourthFieldInList);
    }
}
