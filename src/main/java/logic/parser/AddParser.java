package logic.parser;

import logic.command.AddTaskCommand;
import logic.command.Command;
import utils.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddParser {
    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses add commands.
     */
    public static Command parseAddCommand(String partialParsedCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialParsedCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String addType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (addType) {
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommand(arguments);
        default:
            throw new DukeException("Command word not found");
        }

    }
}
