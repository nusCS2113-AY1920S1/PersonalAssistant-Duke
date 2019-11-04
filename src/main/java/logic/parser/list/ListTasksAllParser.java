package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.command.list.ListTasksCommand;
import logic.command.list.ListTasksPicNumCommand;

public class ListTasksAllParser {

    /**
     * Parses add commands.
     */
    public static Command parseListTasksAll(String argument) throws DukeException {

        switch (argument) {
        case ListTasksParser.PICNUM:
            return new ListTasksPicNumCommand();
        case ListCommandParser.NONE:
            return new ListTasksCommand();
        default:
            throw new DukeException(ListTasksParser.LIST_USAGE);
        }

    }
}
