package logic.parser;

import common.DukeException;
import logic.command.ByeCommand;
import logic.command.Command;

public class ByeCommandParser {

    public static final String BYE_USAGE = "'bye' to quit the application";

    //@@author yuyanglin28
    /**
     * bye command parser
     * @param argument should be nothing
     * @return Byecommand
     * @throws DukeException exception
     */
    public static Command parseByeCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            return new ByeCommand();
        } else {
            throw new DukeException(BYE_USAGE);
        }
    }
}
