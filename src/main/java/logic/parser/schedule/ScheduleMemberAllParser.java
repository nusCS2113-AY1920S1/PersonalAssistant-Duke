package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.schedule.ScheduleMemberAllCommand;

public class ScheduleMemberAllParser {

    //@@author yuyanglin28
    /**
     * parse schedule member all
     * @param argument content after all, member name
     * @return ScheduleMemberAllCommand
     * @throws DukeException throw exception when member name is empty
     */
    public static Command parseScheduleMemberAll(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(ScheduleMemberParser.SCHEDULE_USAGE);
        } else {
            argument = argument.trim();
            return new ScheduleMemberAllCommand(argument);
        }
    }
}
