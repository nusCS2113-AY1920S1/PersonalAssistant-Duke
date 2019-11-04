package duke.logic.parsers.commandparsers;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.MarkDoneCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for DoneCommand.
 */
public class DoneParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Constructs the DoneParser.
     */
    public DoneParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs DoneCommand object.
     * @return DoneCommand object.
     * @throws ParseException If DoneCommand object cannot be created.
     */
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, ONE, input);
        return new MarkDoneCommand(index);
    }
}
