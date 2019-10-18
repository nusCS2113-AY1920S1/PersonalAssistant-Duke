package duke.parsers;

import duke.commands.MarkDoneCommand;
import duke.exceptions.DukeException;

public class DoneCommandParser implements ParserInterface<MarkDoneCommand> {

    @Override
    public MarkDoneCommand parse(String userInput) throws DukeException {
        String name = userInput.split(" /date ", 2)[0];
        if (userInput.split(" /date ").length > 1) {
            String date = userInput.split(" /date ")[1];
            return new MarkDoneCommand(name, date);
        } else {
            throw new DukeException("\u2639 OOPS!!! The done command was not entered correctly");
        }
    }
}
