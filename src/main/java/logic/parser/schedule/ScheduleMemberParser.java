package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleMemberParser {

    static final String SCHEDULE_USAGE = "Usage: schedule member {all/todo} [MEMBER_NAME]";

    //@@author yuyanglin28

    /**
     * parse schedule member
     * @param partialCommand content after member, divide to all or todo type
     * @return a schedule member related logic.command
     * @throws DukeException exception
     */
    public static Command parseScheduleMember(String partialCommand) throws DukeException {

        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(SCHEDULE_USAGE);
        }

        String scheduleType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        scheduleType = scheduleType.trim();

        switch (scheduleType) {
        case ScheduleCommandParser.ALL:
            return ScheduleMemberAllParser.parseScheduleMemberAll(arguments);
        case ScheduleCommandParser.TODO:
            return ScheduleMemberTodoParser.parseScheduleMemberTodo(arguments);
        default:
            throw new DukeException(SCHEDULE_USAGE);
        }

    }
}
