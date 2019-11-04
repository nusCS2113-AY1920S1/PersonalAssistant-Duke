package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.command.list.ListTasksCommand;
import logic.command.list.ListTasksPicNumCommand;

public class ListTasksAllParser {

    //@@author yuyanglin28
    /**
     * parse list all task command, according to list type, picnum/'nothing'
     * @param argument command after all
     * @return a list all task command
     * @throws DukeException throw exception when list type is not correct
     */
    public static Command parseListTasksAll(String argument) throws DukeException {

        switch (argument) {
        case ListTasksParser.PICNUM:
            return new ListTasksPicNumCommand();
        case ListCommandParser.NONE:
            return new ListTasksCommand();
        default:
            throw new DukeException(ListTasksParser.LIST_TASK_USAGE);
        }

    }
}
