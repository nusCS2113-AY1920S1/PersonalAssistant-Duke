package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.MarkDoneCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for DoneCommand.
 */
public class DoneParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Constructs the DoneParser.
     *
     * @param input The user input.
     */
    public DoneParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs DoneCommand object.
     * 
     * @return DoneCommand object.
     * @throws ParseException If DoneCommand object cannot be created.
     */
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, ONE, input);
        return new MarkDoneCommand(index);
    }
}
