package duke.parsers;

import duke.commands.HelpCommand;
import duke.exceptions.DukeException;

public class HelpCommandParser implements ParserInterface<HelpCommand> {

    @Override
    public HelpCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() >= 0) {
            return new HelpCommand(userInput);
        }
        return new HelpCommand();
    }
}
