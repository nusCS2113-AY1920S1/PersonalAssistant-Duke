package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for DeleteCommand.
 */
public class DeleteParser extends CommandParser {

    private int index;

    /**
     * Parses user input into index.
     * @param input The User input
     */
    public DeleteParser(String input) throws DukeException {
        index = ParserUtil.getIntegerIndexInList(0, 1, input);
    }

    /**
     * Constructs DeleteCommand object.
     * @return DeleteCommand object
     */
    public Command parse() throws DukeException {
        return new DeleteCommand(index);
    }
}
