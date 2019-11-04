package logic.parser.list;

import logic.command.Command;
import common.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListCommandParser {

    public static final String LIST_USAGE = "Usage: list tasks {all/todo} (picnum) or list members (todonum/progress)";
    private static final Pattern BASIC_LIST_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String TASK = "tasks";
    private static final String MEMBER = "members";
    public static final String NONE = "";

    /**
     * Parses add commands.
     */
    public static Command parseListCommand(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_LIST_COMMAND_FORMAT.matcher(partialCommand.trim());
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
        default:
            throw new DukeException(LIST_USAGE);
        }

    }

}
