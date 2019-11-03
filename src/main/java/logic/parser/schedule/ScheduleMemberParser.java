package logic.parser.schedule;

import common.DukeException;
import logic.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleMemberParser {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String ALL = "all";
    private static final String TODO = "todo";
    private static final String SCHEDULE_USAGE = "Usage: schedule [team/member] [all/todo] {member name}";

    //@@author yuyanglin28

    /**
     * parse schedule member
     * @param partialCommand content after member, divide to all or todo type
     * @return a schedule member related command
     * @throws DukeException exception
     */
    public static Command parseScheduleMember(String partialCommand) throws DukeException {

        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid" + "\n" + SCHEDULE_USAGE + "\n");
        }

        String scheduleType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        scheduleType = scheduleType.trim();

        switch (scheduleType) {
        case ALL:
            return ScheduleMemberAllParser.parseScheduleMemberAll(arguments);
        case TODO:
            return ScheduleMemberTodoParser.parseScheduleMemberTodo(arguments);
        default:
            throw new DukeException(SCHEDULE_USAGE);
        }

    }
}
