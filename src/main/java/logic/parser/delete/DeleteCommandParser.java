package logic.parser.delete;

import logic.command.Command;
import common.DukeException;
import logic.command.delete.DeleteMemberCommand;
import logic.parser.NewParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommandParser {

    private static final String DELETE_USAGE = "Usage: delete {task/member} {[index].../[MEMBER_NAME],...}";
    private static final String TASK = "task";
    private static final String MEMBER = "member";

    //@@author yuyanglin28
    /**
     * parse delete logic.command, pass to task or member
     * @param partialCommand logic.command after delete
     * @return a delete logic.command
     * @throws DukeException throw exception when delete type is not correct
     */
    public static Command parseDeleteCommand(String partialCommand) throws DukeException {
        final Matcher matcher = NewParser.BASIC_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(DELETE_USAGE);
        }

        String deleteType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        deleteType = deleteType.trim();

        switch (deleteType) {
        case TASK:
            return DeleteTaskParser.parseDeleteTask(arguments);
        case MEMBER:
            return DeleteMemberParser.parseDeleteMember(arguments);
        default:
            throw new DukeException(DELETE_USAGE);
        }

    }
}
