package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleCommandParser {

    private static final String TASKS = "tasks";
    private static final String MEMBER = "member";
    static final String ALL = "all";
    static final String TODO = "todo";
    private static final String SCHEDULE_USAGE = "Usage: schedule {tasks/member} {all/todo} ([MEMBER_NAME])";

    //@@author yuyanglin28
    /**
     * parse the schedule command
     *
     * @param partialCommand content after schedule
     * @return a schedule related command
     * @throws DukeException throw exception when schedule type is not correct
     */
    public static Command parseScheduleCommand(String partialCommand) throws DukeException {

        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(SCHEDULE_USAGE);
        }

        String scheduleType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        scheduleType = scheduleType.trim();

        switch (scheduleType) {
        case TASKS:
            return ScheduleTeamParser.parseScheduleTeam(arguments);
        case MEMBER:
            return ScheduleMemberParser.parseScheduleMember(arguments);
        default:
            throw new DukeException(SCHEDULE_USAGE);
        }

    }
}
