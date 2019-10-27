package duke.logic.parsers;

import duke.logic.commands.ListCommand;
import duke.commons.exceptions.DukeException;

/**
 * Parser class to handle a list command.
 */
public class ListCommandParser implements ParserInterface<ListCommand> {

    /**
     * Parse userInput and return ListCommand.
     * @param userInput String input by user.
     * @return <code>ListCommand</code>
     * @throws DukeException if the user input cannot be parsed
     */
    @Override
    public ListCommand parse(String userInput) throws DukeException {
        return new ListCommand(userInput);
    }
}
