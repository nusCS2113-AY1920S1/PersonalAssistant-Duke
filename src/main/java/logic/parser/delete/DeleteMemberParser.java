package logic.parser.delete;

import logic.command.Command;
import logic.command.delete.DeleteMemberCommand;
import common.DukeException;

public class DeleteMemberParser {

    private static final String EMPTY_NAME = "Member name cannot be empty. Please use comma to separate names.";

    //@@author yuyanglin28

    /**
     * parse delete member command, pass member name to command
     * @return delete member command
     * @throws DukeException throw exception when member name is empty
     */
    public static Command parseDeleteMember(String arguments) throws DukeException {

        String[] names = null;

        if (arguments != null) {
            arguments = arguments.trim();
            names = arguments.split(",");
            for (int i = 0; i < names.length; i++) {
                names[i] = names[i].trim();
            }
        }

        if (names != null) {
            return new DeleteMemberCommand(names);
        } else {
            throw new DukeException(EMPTY_NAME);
        }
    }
}
