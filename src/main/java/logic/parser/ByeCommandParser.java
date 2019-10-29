package logic.parser;

import common.DukeException;
import logic.command.ByeCommand;
import logic.command.Command;
import logic.command.FindCommand;

public class ByeCommandParser {
    public static final String BYE_USAGE = "'bye' to quit the application";
    public static Command parseByeCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            return new ByeCommand();
        } else {
            throw new DukeException(BYE_USAGE);
        }
    }
}
