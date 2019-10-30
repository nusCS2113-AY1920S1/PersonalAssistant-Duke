package logic.parser;

import logic.command.Command;
import logic.command.ListMembersCommand;
import logic.command.ListTasksCommand;
import common.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListCommandParser {

    public static final String LIST_USAGE = "Usage: list [tasks/members]";
    private static final Pattern BASIC_LIST_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses add commands.
     */
    public static Command parseListCommand(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_LIST_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException(LIST_USAGE);
        }

        final String listType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (listType) {
        case ListTasksCommand.COMMAND_WORD:
            return new ListTasksCommand(arguments);

        case ListMembersCommand.COMMAND_WORD:
            return new ListMembersCommand(arguments);

        default:
            throw new DukeException(LIST_USAGE);
        }

    }

}
