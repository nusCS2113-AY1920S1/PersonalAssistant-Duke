package duke.logic.parsers;

import duke.logic.commands.DeleteCommand;
import duke.commons.exceptions.DukeException;

public class DeleteCommandParser implements ParserInterface<DeleteCommand> {

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
