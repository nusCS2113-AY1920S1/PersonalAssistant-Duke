package duke.parser;

import duke.logic.commands.Command;
import duke.logic.commands.HelpCommand;

public class HelpCommandParser {

    public Command parse() {
        return new HelpCommand();
    }
}
