package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.HelpCommand;

public class HelpCommandParser {

    public static final String HELP_USAGE = "'help' to get command help message.";

    public static Command parseHelpCommand(String argument) throws DukeException{
        if (argument.equals("")) {
            return new HelpCommand();
        } else {
            throw new DukeException(HELP_USAGE);
        }
    }
}
