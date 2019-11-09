package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteShowCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteShowCommand.
 */
public class RouteShowParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int TWO = 2;

    /**
     * Constructs the RouteShowParser.
     *
     * @param input The user input.
     */
    public RouteShowParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteShowCommand object.
     *
     * @return The RouteShowCommand object.
     * @throws ParseException If RouteShowCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, TWO, input);
        return new RouteShowCommand(index);
    }
}
