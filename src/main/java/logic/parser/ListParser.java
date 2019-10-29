package logic.parser;

import logic.command.Command;
import utils.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListParser {
    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static Command parseListCommand(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String listType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch(listType) {

            default:
                throw new DukeException("unknown list command");
        }

    }
}
