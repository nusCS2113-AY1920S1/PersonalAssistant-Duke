package logic.parser.edit;

import common.DukeException;
import logic.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTaskParser {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final String EDIT_USAGE = "Usage: edit [task] [time/name/des] [index] /to ...";
    public static final String TIME = "time";
    public static final String NAME = "name";
    public static final String DES = "des";


    //@@author yuyanglin28
    /**
     * parse delete command, divide to task or member
     * @param partialCommand argument part of the command
     * @return a delete command
     * @throws DukeException exception
     */
    public static Command parseEditTask(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid" + "\n" + EDIT_USAGE + "\n");
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
