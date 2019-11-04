package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListTasksParser {

    static final String LIST_TASK_USAGE = "Usage: list tasks {all/todo} (picnum)";
    private static final String ALL = "all";
    private static final String TODO = "todo";
    static final String PICNUM = "picnum";

    //@@author yuyanglin28
    /**
     * parse list task command, according to the list type, todo/all
     * @param partialCommand command after task
     * @return a list task command
     * @throws DukeException throw exception when command pattern or list type is not correct
     */
    public static Command parseListTasks(String partialCommand) throws DukeException {
        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(LIST_TASK_USAGE);
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
            throw new DukeException(LIST_TASK_USAGE);
        }

    }
}
