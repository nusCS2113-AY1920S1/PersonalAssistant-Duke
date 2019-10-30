package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.HelpCommand;

public class HelpCommandParser {

    public static final String HELP_USAGE = "'help' to get command help message.";

    //@@author yuyanglin28
    /**
     * parse help command
     * @param argument should be nothing
     * @return help command
     * @throws DukeException exception
     */
    public static Command parseHelpCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            return new HelpCommand();
        } else {
            throw new DukeException(HELP_USAGE);
        }
    }
}
