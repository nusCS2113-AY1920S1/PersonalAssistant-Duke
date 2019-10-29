package logic.parser;

import logic.command.Command;
import logic.command.DeleteMemberCommand;
import logic.command.DeleteTaskCommand;
import utils.DukeException;

public class DeleteMemberParser {


    public static final String MEMBER_NO_NAME_MESSAGE = "Member name cannot be empty.";

    public static Command parseDeleteMember(String name) throws DukeException {

        if (name != null) {
            return new DeleteMemberCommand(name);
        } else {
            throw new DukeException(MEMBER_NO_NAME_MESSAGE);
        }
    }
}
