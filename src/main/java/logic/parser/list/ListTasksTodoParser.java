package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.command.list.ListTasksTodoCommand;
import logic.command.list.ListTasksTodoPicNumCommand;

public class ListTasksTodoParser {

    //@@author yuyanglin28
    /**
     * parse list todo task command, according to list type, picnum/'nothing'
     * @param argument command after todo
     * @return a list todo task command
     * @throws DukeException throw exception when list type is not correct
     */
    public static Command parseListTasksTodo(String argument) throws DukeException {

        switch (argument) {
        case ListTasksParser.PICNUM:
            return new ListTasksTodoPicNumCommand();
        case ListCommandParser.NONE:
            return new ListTasksTodoCommand();
        default:
            throw new DukeException(ListTasksParser.LIST_TASK_USAGE);
        }

    }
}
