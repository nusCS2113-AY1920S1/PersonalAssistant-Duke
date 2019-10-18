package duke.parsers;

import duke.commands.ListCommand;
import duke.exceptions.DukeException;

public class ListCommandParser implements ParserInterface<ListCommand> {

    @Override
    public ListCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() != 0) {
            return new ListCommand(userInput);
        } else {
            return new ListCommand();
        }
    }
}
