package logic.parser;

import logic.command.*;
import utils.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommandParser {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final String TASK = "task";
    public static final String MEMBER = "member";

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
                throw new DukeException("Command word not found");
        }

    }
}
