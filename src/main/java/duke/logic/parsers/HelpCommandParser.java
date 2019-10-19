package duke.logic.parsers;

import duke.logic.commands.HelpCommand;
import duke.commons.exceptions.DukeException;

public class HelpCommandParser implements ParserInterface<HelpCommand> {

    @Override
    public HelpCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() >= 0) {
            return new HelpCommand(userInput);
        }
        return new HelpCommand();
    }
}
