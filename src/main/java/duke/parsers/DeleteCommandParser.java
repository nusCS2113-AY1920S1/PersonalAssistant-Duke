package duke.parsers;

import duke.commands.DeleteCommand;
import duke.exceptions.DukeException;

public class DeleteCommandParser implements ParserInterface<DeleteCommand> {

    @Override
    public DeleteCommand parse(String UserInput) throws DukeException {
        if (UserInput.trim().length() != 0) {
            // user specifies date and index.
            if (UserInput.split("/date").length >= 2) {
                String[] splitArgs = UserInput.split("/date", 2);
                return new DeleteCommand(splitArgs[0], splitArgs[1]);
            } else {
                // user only specifies index to delete for current day.
                return new DeleteCommand(UserInput);
            }
        } else {
            throw new DukeException("Please enter index of meal to delete on today's list or "
                    + "date and index of meal to delete");
        }
    }
}
