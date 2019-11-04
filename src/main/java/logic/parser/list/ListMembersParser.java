package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.command.list.ListMembersCommand;
import logic.command.list.ListMembersProgressCommand;
import logic.command.list.ListMembersTodoNumCommand;

public class ListMembersParser {

    private static final String LIST_MEMBER_USAGE = "Usage: list members (todonum/progress)";
    private static final String TODO = "todonum";
    private static final String PROGRESS = "progress";

    //@@author yuyanglin28
    /**
     * parse list member command, according to the list type, todonum/progress/'nothing'
     * @param argument command after members
     * @return a list member command
     * @throws DukeException throw exception when list type is not correct
     */
    static Command parseListMembers(String argument) throws DukeException {

        switch (argument) {
        case TODO:
            return new ListMembersTodoNumCommand();
        case PROGRESS:
            return new ListMembersProgressCommand();
        case ListCommandParser.NONE:
            return new ListMembersCommand();
        default:
            throw new DukeException(LIST_MEMBER_USAGE);
        }

    }
}
