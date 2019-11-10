package logic.parser;

import common.DukeException;
import logic.command.CheckCommand;
import logic.command.Command;
import logic.command.HelpCommand;

public class CheckCommandParser {
    public static final String CHECK_USAGE = "Usage: check";

    //@@author yuyanglin28
    /**
     * This method is to parse check command
     * @param argument should be nothing
     * @return check command
     * @throws DukeException throw exception when command after check is not empty.
     */
    public static Command parseCheckCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            return new CheckCommand();
        } else {
            throw new DukeException(CHECK_USAGE);
        }
    }
}
