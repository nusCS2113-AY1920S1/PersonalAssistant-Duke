package logic.parser.edit;

import common.DukeException;
import logic.command.Command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditCommandParser {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final String EDIT_USAGE = "Usage: edit [member] [name/bio/email/phone] [index] /to ... "
                                            + "or: edit [task] [time/des] [index] /to ...";
    public static final String TASK = "task";
    public static final String MEMBER = "member";

    //@@author yuyanglin28
    /**
     * parse delete command, divide to task or member
     * @param partialCommand argument part of the command
     * @return a delete command
     * @throws DukeException exception
     */
    public static Command parseEditCommand(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid" + "\n" + EDIT_USAGE + "\n");
        }

        String editType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        editType = editType.trim();

        switch (editType) {
        case TASK:
            return EditTaskParser.parseEditTask(arguments);
        case MEMBER:
            return EditMemberParser.parseEditMember(arguments);
        default:
            throw new DukeException(EDIT_USAGE);
        }

    }
}
