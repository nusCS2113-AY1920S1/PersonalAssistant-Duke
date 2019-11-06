package logic.parser.list;

import logic.command.Command;
import common.DukeException;
import logic.parser.NewParser;

import java.util.regex.Matcher;

public class ListCommandParser {

    private static final String LIST_USAGE = "Usage: list tasks {all/todo} (picnum),\n" +
            "list members (todonum/progress),\nor list reminders";
    private static final String TASK = "tasks";
    private static final String MEMBER = "members";
    private static final String REMINDER = "reminders";
    static final String NONE = "";

    //@@author yuyanglin28

    /**
     * parse list command, according to the list type after list, task/member
     *
     * @param partialCommand command after list
     * @return a list command
     * @throws DukeException throw exception when the pattern or list type not correct
     */
    public static Command parseListCommand(String partialCommand) throws DukeException {
        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(LIST_USAGE);
        }

        String listType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        listType = listType.trim();
        arguments = arguments.trim();

        switch (listType) {
            case TASK:
                return ListTasksParser.parseListTasks(arguments);
            case MEMBER:
                return ListMembersParser.parseListMembers(arguments);
            case REMINDER:
                return ListRemindersParser.parseReminders(arguments);
            default:
                throw new DukeException(LIST_USAGE);
        }

    }

}
