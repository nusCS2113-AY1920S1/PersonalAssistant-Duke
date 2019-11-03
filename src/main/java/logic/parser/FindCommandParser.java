package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.FindCommand;
import model.Model;

public class FindCommandParser {

    public static final String FIND_USAGE = "usage: find [keyword]";

    /**
     * parse find command
     * @param argument keyword
     * @return findCommand
     * @throws DukeException exception
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
