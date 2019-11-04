package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.command.list.ListMembersCommand;
import logic.command.list.ListMembersProgressCommand;
import logic.command.list.ListMembersTodoNumCommand;

public class ListMembersParser {

    public static final String LIST_USAGE = "Usage: list members (todonum/progress)";
    private static final String TODO = "todonum";
    private static final String PROGRESS = "progress";

    /**
     * Parses add commands.
     */
    public static Command parseListMembers(String argument) throws DukeException {

        switch (argument) {
        case TODO:
            return new ListMembersTodoNumCommand();
        case PROGRESS:
            return new ListMembersProgressCommand();
        case ListCommandParser.NONE:
            return new ListMembersCommand();
        default:
            throw new DukeException(LIST_USAGE);
        }

    }
}
