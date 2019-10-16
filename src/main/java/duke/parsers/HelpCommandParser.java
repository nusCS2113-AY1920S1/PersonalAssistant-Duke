package duke.parsers;

import duke.commands.HelpCommand;
import duke.exceptions.DukeException;

public class HelpCommandParser implements ParserInterface<HelpCommand> {

    @Override
    public HelpCommand parse(String UserInput) throws DukeException {
        if (UserInput.trim().length() >= 0) {
            return new HelpCommand(UserInput);
        }
        return new HelpCommand();
    }
}
