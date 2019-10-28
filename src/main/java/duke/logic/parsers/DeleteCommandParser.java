package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.DeleteCommand;

/**
 * Parser class to handle deletion of a single item from model.
 */
public class DeleteCommandParser implements ParserInterface<DeleteCommand> {

    /**
     * Parse user input and return DeleteCommand.
     * @param userInput String input by user.
     * @return <code>DeleteCommand</code> Command object demarcating the entry of data to be deleted
     */
    @Override

    public DeleteCommand parse(String userInput)  {
        try {
            InputValidator.validate(userInput);
            String[] indexAndDate = ArgumentSplitter.splitArguments(userInput, "/date");
            if (indexAndDate[1].equals("")) {
                return new DeleteCommand(indexAndDate[0]);
            } else {
                return new DeleteCommand(indexAndDate[0], indexAndDate[1]);
            }
        } catch (DukeException e) {
            return new DeleteCommand(false,"Please enter index of meal to delete on today's list or "
                    + "date and index of meal to delete");
        }
    }
}
