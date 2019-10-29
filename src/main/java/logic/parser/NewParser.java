package logic.parser;

import commands.DeleteCommand;
import logic.command.Command;
import utils.DukeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String ADD_COMMAND_WORD = "ADD";
    private static final String LIST_COMMAND_WORD = "LIST";
    private static final String DELETE_COMMAND_WORD = "DELETE";
    private static final String DONE_COMMAND_WORD = "DONE";
    public static final String LINK_COMMAND_WORD = "LINK";

    //@@author JustinChia1997

    /**
     * <p>Parse a command line String to a Commands.Command object.</p>
     *
     * @param fullCommand the input command line String
     * @return the new Commands.Command object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Command parseCommand(String fullCommand) throws DukeException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(fullCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments").trim();

        commandWord = commandWord.trim().toUpperCase();

        String[] dict = {
            "ADD", "LIST", "DONE", "BYE", "DELETE", "FIND", "SNOOZE",
            "SCHEDULE", "CHECK", "LINK", "UNLINK", "REMOVE", "HELP"
        };

        commandWord = SpellingErrorCorrector.commandCorrector(dict, commandWord);

        switch (commandWord) {

            case ADD_COMMAND_WORD:
                return AddCommandParser.parseAddCommand(arguments);
            case LIST_COMMAND_WORD:
                return ListCommandParser.parseListCommand(arguments);
            case DONE_COMMAND_WORD:
                return DoneCommandParser.parseDoneCommand(arguments);
            case LINK_COMMAND_WORD:
                return LinkCommandParser.parseLinkCommand(arguments);
            case DELETE_COMMAND_WORD:
                return DeleteCommandParser.parseDeleteCommand(arguments);
            default:
                throw new DukeException("Command not found");
        }

    }
}

