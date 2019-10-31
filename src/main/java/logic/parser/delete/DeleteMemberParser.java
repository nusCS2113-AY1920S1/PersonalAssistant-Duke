package logic.parser.delete;

import logic.command.Command;
import logic.command.delete.DeleteMemberCommand;
import common.DukeException;

public class DeleteMemberParser {


    public static final String MEMBER_NO_NAME_MESSAGE = "Member name cannot be empty.";

    //@@author yuyanglin28

    /**
     * parse delete member command, pass member name to command
     * @return delete member command
     * @throws DukeException exception
     */
    public static Command parseDeleteMember(int[] indexes) throws DukeException {

        if (indexes != null) {
            return new DeleteMemberCommand(indexes);
        } else {
            throw new DukeException(MEMBER_NO_NAME_MESSAGE);
        }
    }
}
