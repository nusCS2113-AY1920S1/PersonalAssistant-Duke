package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.command.list.ListRemindersCommand;

public class ListRemindersParser {

    //@@author yuyanglin28
    /**
     * parse list todo task command, according to list type, picnum/'nothing'
     * @param argument command after todo
     * @return a list todo task command
     * @throws DukeException throw exception when list type is not correct
     */
    public static Command parseReminders(String argument) throws DukeException {
        return new ListRemindersCommand();
    }
}
