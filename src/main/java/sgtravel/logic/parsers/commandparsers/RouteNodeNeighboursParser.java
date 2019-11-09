package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteNodeNeighboursCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteNodeNeighboursCommand.
 */
public class RouteNodeNeighboursParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    /**
     * Constructs the RouteNodeNeighboursCommand.
     *
     * @param input The user input.
     */
    public RouteNodeNeighboursParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteNodeNeighboursCommand object.
     *
     * @return RouteNodeNeighboursCommand object.
     * @throws ParseException If RouteNodeNeighboursCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int firstIndex = ParserUtil.getIntegerIndexInList(ZERO, TWO, input);
        int secondIndex = ParserUtil.getIntegerIndexInList(ONE, TWO, input);
        return new RouteNodeNeighboursCommand(firstIndex, secondIndex);
    }
}
