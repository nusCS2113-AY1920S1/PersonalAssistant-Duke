package duke.parsers;

import duke.commands.ListCommand;
import duke.exceptions.DukeException;

public class ListCommandParser implements ParserInterface<ListCommand> {

    @Override
    public ListCommand parse(String UserInput) throws DukeException {
        if (UserInput.trim().length() != 0) {
            return new ListCommand(UserInput);
        } else {
            return new ListCommand();
        }
    }
}
