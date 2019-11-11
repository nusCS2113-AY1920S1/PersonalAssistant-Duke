package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditCommandParser {

    public static final String EDIT_USAGE = "Usage: edit member {name/bio/email/phone} [MEMBER_NAME] "
                                            + "/to [NEW_CONTENT]\n"
                                            + "or edit task {name/time/des} INDEX /to [NEW_CONTENT]";
    public static final String TASK = "task";
    public static final String MEMBER = "member";

    //@@author yuyanglin28
    /**
     * parse edit logic.command, pass to task or member
     * @param partialCommand logic.command after edit
     * @return a edit logic.command
     * @throws DukeException throw exception when edit type is not correct.
     */
    public static Command parseEditCommand(String partialCommand) throws DukeException {
        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(EDIT_USAGE);
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
