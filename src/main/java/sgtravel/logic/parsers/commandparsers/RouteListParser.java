package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteListCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteListCommand.
 */
public class RouteListParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Constructs the RouteListParser.
     */
    public RouteListParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteListCommand object.
     * @return RouteListCommand object.
     * @throws ParseException If RouteListCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, ONE, input);
        return new RouteListCommand(index);
    }
}
