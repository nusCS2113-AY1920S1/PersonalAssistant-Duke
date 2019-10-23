package logic.parser;

import logic.command.Command;
import logic.command.HiCommand;
import utils.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewParser {
    /**
     * Used for initial separation of command word and args.
     *
     * @author Justin Chia
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String ADD_COMMAND_WORD = "add";

    /**
     * General top level parser.
     * */
    public static Command parseCommand(String fullCommand) throws DukeException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(fullCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case HiCommand.COMMAND_WORD:
            return new HiCommand();

        case ADD_COMMAND_WORD:
            return AddParser.parseAddCommand(arguments);

        default:
            throw new DukeException("Command word not found");
        }

    }


}
