package duke.logic.parsers.commandParsers;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.QuickEditCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for QuickEditCommand.
 */
public class QuickEditParser extends CommandParser {
    private String inputBody;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    /**
     * Constructs the QuickEditParser.
     */
    public QuickEditParser(String inputBody) {
        this.inputBody = inputBody;
    }

    /**
     * Parses the user input and constructs QuickEditCommand object.
     * @return QuickEditCommand object.
     * @throws ParseException If QuickEditCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, FOUR, inputBody);
        String firstField = ParserUtil.getFieldInList(ONE, FOUR, inputBody);
        String secondField = ParserUtil.getFieldInList(TWO, FOUR, inputBody);
        String thirdField = ParserUtil.getFieldInList(THREE, FOUR, inputBody);
        return new QuickEditCommand(index, firstField, secondField, thirdField);
    }
}
