package logic.parser;

import logic.command.Command;
import common.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddCommandParser {
    public static final String FORMAT_WRONG_MESSAGE = "Cannot resolve the model type. "
            + "\nUsage: add [task/member] [details]";

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final String TASK = "task";
    public static final String MEMBER = "member";
    //@@author JustinChia1997

    /**
     * Parses add commands.
     */
    public static Command parseAddCommand(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String addType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (addType) {
        case TASK:
            return AddTaskParser.parseAddTask(arguments);
        case MEMBER:
            return AddMemberParser.parseAddMember(arguments);
        default:
            throw new DukeException("Command word not found");
        }

    }


}
