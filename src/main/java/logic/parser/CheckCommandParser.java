package logic.parser;

import common.DukeException;
import logic.command.CheckCommand;
import logic.command.Command;
import logic.command.HelpCommand;

public class CheckCommandParser {
    public static final String CHECK_USAGE = "'check' to check time crash.";

    //@@author yuyanglin28
    /**
     * parse help command
     * @param argument should be nothing
     * @return help command
     * @throws DukeException exception
     */
    public static Command parseCheckCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            return new CheckCommand();
        } else {
            throw new DukeException(CHECK_USAGE);
        }
    }
}
