package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTaskParser {

    public static final String EDIT_USAGE = "Usage: edit task {name/time/des} INDEX /to [NEW_CONTENT]";

    public static final String TIME = "time";
    public static final String NAME = "name";
    public static final String DES = "des";
    public static final String INDEX_NO_EMPTY = "The index of task shouldn't be empty.";
    public static final String GET_INDEX_FAIL = "Wrong index format.";


    //@@author yuyanglin28
    /**
     * parse edit task logic.command, pass to time, name or description
     * @param partialCommand logic.command after task
     * @return edit task logic.command
     * @throws DukeException throw exception when edit type is not correct
     */
    public static Command parseEditTask(String partialCommand) throws DukeException {
        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(EDIT_USAGE);
        }

        String editType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        editType = editType.trim();

        switch (editType) {
        case TIME:
            return EditTaskDateTimeParser.parseEditTaskDateTime(arguments);
        case NAME:
            return EditTaskNameParser.parseEditTaskName(arguments);
        case DES:
            return EditTaskDesParser.parseEditTaskDes(arguments);
        default:
            throw new DukeException(EDIT_USAGE);
        }

    }
}
