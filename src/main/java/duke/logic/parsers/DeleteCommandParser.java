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
        if (userInput.trim().length() != 0) {
            // user specifies date and index.
            if (userInput.split("/date").length >= 2) {
                String[] splitArgs = userInput.split("/date", 2);
                return new DeleteCommand(splitArgs[0], splitArgs[1]);
            } else {
                // user only specifies index to delete for current day.
                return new DeleteCommand(userInput);
            }
        } else {
            throw new DukeException("Please enter index of meal to delete on today's list or "
                    + "date and index of meal to delete");
        }
    }
}
