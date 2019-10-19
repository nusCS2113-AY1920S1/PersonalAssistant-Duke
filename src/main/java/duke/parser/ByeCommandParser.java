package duke.parser;

import duke.logic.commands.ByeCommand;
import duke.logic.commands.Command;

public class ByeCommandParser {

    public Command parse() {
        return new ByeCommand();
    }
}
