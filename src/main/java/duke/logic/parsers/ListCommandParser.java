package duke.logic.parsers;

import duke.logic.commands.ListCommand;
import duke.commons.exceptions.DukeException;

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
