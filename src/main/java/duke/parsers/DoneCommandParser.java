package duke.parsers;

import duke.commands.MarkDoneCommand;
import duke.exceptions.DukeException;

public class DoneCommandParser implements ParserInterface<MarkDoneCommand> {

    @Override
    public MarkDoneCommand parse(String UserInput) throws DukeException {
        String name = UserInput.split(" /date ", 2)[0];
        if (UserInput.split(" /date ").length > 1) {
            String date = UserInput.split(" /date ")[1];
            return new MarkDoneCommand(name, date);
        } else {
            throw new DukeException("\u2639 OOPS!!! The done command was not entered correctly");
        }
    }
}
