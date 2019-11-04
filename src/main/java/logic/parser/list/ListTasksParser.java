package logic.parser.list;

import common.DukeException;
import logic.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListTasksParser {

    public static final String LIST_USAGE = "Usage: list tasks {all/todo} (picnum)";
    private static final Pattern BASIC_LIST_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String ALL = "all";
    private static final String TODO = "todo";
    public static final String PICNUM = "picnum";

    /**
     * Parses add commands.
     */
    public static Command parseListTasks(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_LIST_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(LIST_USAGE);
        }

        String listType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        listType = listType.trim();
        arguments = arguments.trim();

        switch (listType) {
        case ALL:
            return ListTasksAllParser.parseListTasksAll(arguments);

        case TODO:
            return ListTasksTodoParser.parseListTasksTodo(arguments);

        default:
             throw new DukeException(LIST_USAGE);
        }

    }
}
