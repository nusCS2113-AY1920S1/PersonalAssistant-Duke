package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteDeleteCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteDeleteCommand.
 */
public class RouteDeleteParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Constructs the RouteDeleteParser.
     *
     * @param input The user input.
     */
    public RouteDeleteParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteDeleteCommand object.
     *
     * @return The RouteDeleteCommand object.
     * @throws ParseException If RouteDeleteCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, ONE, input);
        return new RouteDeleteCommand(index);
    }
}
