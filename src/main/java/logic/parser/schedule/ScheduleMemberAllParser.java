package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.command.ScheduleMemberAllCommand;

public class ScheduleMemberAllParser {

    private static final String SCHEDULE_USAGE = "Usage: schedule [team/member] [all/todo] {member name}";

    public static Command parseScheduleMemberAll(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(SCHEDULE_USAGE);
        } else {
            String memberName = argument.trim();
            return new ScheduleMemberAllCommand(memberName);
        }
    }
}
