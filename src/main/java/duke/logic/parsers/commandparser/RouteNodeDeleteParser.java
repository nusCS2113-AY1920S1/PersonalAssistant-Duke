package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.RouteNodeDeleteCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteNodeDeleteCommand.
 */
public class RouteNodeDeleteParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    /**
     * Constructs the RouteNodeDeleteParser.
     */
    public RouteNodeDeleteParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteNodeDeleteCommand object.
     * @return RouteNodeDeleteCommand object.
     * @throws ParseException If RouteNodeDeleteCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int firstIndex = ParserUtil.getIntegerIndexInList(ZERO, TWO, input);
        int secondIndex = ParserUtil.getIntegerIndexInList(ONE, TWO, input);
        return new RouteNodeDeleteCommand(firstIndex, secondIndex);
    }
}
