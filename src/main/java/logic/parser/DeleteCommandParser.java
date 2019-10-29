package logic.parser;

import logic.command.Command;
import common.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommandParser {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final String DELETE_USAGE = "Usage: delete [task/member] [index/member name]";
    public static final String TASK = "task";
    public static final String MEMBER = "member";

    //@@author yuyanglin28
    /**
     * parse delete command, divide to task or member
     * @param partialCommand argument part of the command
     * @return a delete command
     * @throws DukeException exception
     */
    public static Command parseDeleteCommand(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String addType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (addType) {
        case TASK:
            return DeleteTaskParser.parseDeleteTask(arguments);
        case MEMBER:
            return DeleteMemberParser.parseDeleteMember(arguments);
        default:
            throw new DukeException(DELETE_USAGE);
        }

    }
}
