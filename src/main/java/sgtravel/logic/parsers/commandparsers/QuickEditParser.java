package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.QuickEditCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for QuickEditCommand.
 */
public class QuickEditParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    /**
     * Constructs the QuickEditParser.
     *
     * @param input The user input.
     */
    public QuickEditParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs QuickEditCommand object.
     *
     * @return QuickEditCommand object.
     * @throws ParseException If QuickEditCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, FOUR, input);
        String firstField = ParserUtil.getFieldInList(ONE, FOUR, input);
        String secondField = ParserUtil.getFieldInList(TWO, FOUR, input);
        String thirdField = ParserUtil.getFieldInList(THREE, FOUR, input);
        return new QuickEditCommand(index, firstField, secondField, thirdField);
    }
}
