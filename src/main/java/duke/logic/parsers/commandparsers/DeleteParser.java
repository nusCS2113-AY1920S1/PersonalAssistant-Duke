package duke.logic.parsers.commandparsers;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for DeleteCommand.
 */
public class DeleteParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    /**
     * Constructs the DeleteParser.
     */
    public DeleteParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs DeleteCommand object.
     * @return DeleteCommand object.
     * @throws ParseException If DeleteCommand object cannot be created.
     */
    public Command parse() throws ParseException {
        int index = ParserUtil.getIntegerIndexInList(ZERO, ONE, input);
        return new DeleteCommand(index);
    }
}
