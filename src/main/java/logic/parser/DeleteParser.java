package logic.parser;

import logic.command.Command;
import logic.command.DeleteTaskCommand;
import utils.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteParser {
    private static final Pattern BASIC_DELETE_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses add commands.
     */
    public static Command parseDeleteCommand(String partialParsedCommand) throws DukeException {
        final Matcher matcher = BASIC_DELETE_COMMAND_FORMAT.matcher(partialParsedCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String deleteType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (deleteType) {
            case DeleteTaskCommand.COMMAND_WORD:
                return new DeleteTaskCommand(arguments);
            default:
                throw new DukeException("Command word not found");
        }

    }
}
