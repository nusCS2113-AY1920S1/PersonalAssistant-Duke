package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTaskParser {

    public static final String EDIT_USAGE = "Usage: edit task {name/time/des} INDEX /to [NEW_CONTENT]";
    public static final String TIME = "time";
    public static final String DES = "des";
    public static final String INDEX_NO_EMPTY = "The index of task shouldn't be empty.";


    //@@author yuyanglin28
    /**
     * parse delete command, divide to task or member
     * @param partialCommand argument part of the command
     * @return a delete command
     * @throws DukeException exception
     */
    public static Command parseEditTask(String partialCommand) throws DukeException {
        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid" + "\n" + EDIT_USAGE + "\n");
        }

        String editType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        editType = editType.trim();

        switch (editType) {
        case TIME:
            return EditTaskDateTimeParser.parseEditTaskDateTime(arguments);
        case DES:
            return EditTaskDesParser.parseEditTaskDes(arguments);
        default:
            throw new DukeException(EDIT_USAGE);
        }

    }
}
