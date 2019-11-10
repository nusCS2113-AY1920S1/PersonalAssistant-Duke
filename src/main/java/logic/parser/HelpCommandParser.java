package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.HelpCommand;

public class HelpCommandParser {

    public static final String HELP_USAGE = "Usage: help";

    //@@author yuyanglin28
    /**
     * This method is to parse help command
     * @param argument should be nothing
     * @return help command
     * @throws DukeException throw exception when command after help is not empty
     */
    public static Command parseHelpCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            return new HelpCommand();
        } else {
            throw new DukeException(HELP_USAGE);
        }
    }
}
