package logic.parser.delete;

import logic.command.Command;
import common.DukeException;
import logic.command.delete.DeleteMemberCommand;

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

        String deleteType = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        deleteType = deleteType.trim();

        int[] indexes = null;

        if (arguments != null) {
            arguments = arguments.trim();
            String[] indexesString = arguments.split("\\s+");
            indexes = new int[indexesString.length];
            for (int i = 0; i < indexes.length; i++) {
                try {
                    int index = Integer.parseInt(indexesString[i]);
                    indexes[i] = index;
                } catch (NumberFormatException e) {
                    throw new DukeException("Wrong number format, please check.");
                }
            }
        }

        switch (deleteType) {
        case TASK:
            return DeleteTaskParser.parseDeleteTask(indexes);
        case MEMBER:
            return DeleteMemberParser.parseDeleteMember(indexes);
        default:
            throw new DukeException(DELETE_USAGE);
        }

    }
}
