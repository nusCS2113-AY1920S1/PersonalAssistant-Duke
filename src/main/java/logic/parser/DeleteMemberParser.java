package logic.parser;

import logic.command.Command;
import logic.command.DeleteMemberCommand;
import common.DukeException;

public class DeleteMemberParser {


    public static final String MEMBER_NO_NAME_MESSAGE = "Member name cannot be empty.";

    //@@author yuyanglin28

    /**
     * parse delete member command, pass member name to command
     * @param input input of user
     * @return delete member command
     * @throws DukeException exception
     */
    public static Command parseDeleteMember(String input) throws DukeException {

        if (input != null) {
            String name = input.trim();
            return new DeleteMemberCommand(name);
        } else {
            throw new DukeException(MEMBER_NO_NAME_MESSAGE);
        }
    }
}
