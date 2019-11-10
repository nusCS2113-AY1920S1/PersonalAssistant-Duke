package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.FindCommand;
import model.Model;

public class FindCommandParser {

    public static final String FIND_USAGE = "Usage: find [KEYWORD]";

    //@@author yuyanglin28
    /**
     * parse find command
     * @param argument keyword to search for
     * @return FindCommand
     * @throws DukeException throw exception when keyword is empty
     */
    public static Command parseFindCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(FIND_USAGE);
        } else {
            String keyword = argument.trim();
            return new FindCommand(keyword);
        }
    }
}
