package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.schedule.ScheduleMemberTodoCommand;

public class ScheduleMemberTodoParser {

    //@@author yuyanglin28
    /**
     * parse schedule member todo
     * @param argument after todo, member name
     * @return ScheduleMemberTodoCommand
     * @throws DukeException throw exception when no member name
     */
    public static Command parseScheduleMemberTodo(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(ScheduleMemberParser.SCHEDULE_USAGE);
        } else {
            argument = argument.trim();
            return new ScheduleMemberTodoCommand(argument);
        }
    }
}
