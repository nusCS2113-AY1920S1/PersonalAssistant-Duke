package duke.logic.parsers;

import duke.logic.commands.DeleteCommand;
import duke.commons.exceptions.DukeException;

/**
 * Parser class to handle deletion of a single item from model.
 */
public class DeleteCommandParser implements ParserInterface<DeleteCommand> {

    /**
     * Parse user input and return DeleteCommand.
     * @param userInput String input by user.
     * @return <code>DeleteCommand</code> Command object demarcating the entry of data to be deleted
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public DeleteCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
        return new DeleteCommand(indexAndDate[0], indexAndDate[1]);
    }
}
