package logic.parser;

import logic.command.Command;
import common.DukeException;
import logic.parser.delete.DeleteCommandParser;
import logic.parser.edit.EditCommandParser;
import logic.parser.schedule.ScheduleCommandParser;

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
    private static final String SNOOZE_COMMAND_WORD = "SNOOZE";
    private static final String RENAME_COMMAND_WORD = "RENAME";
    public static final String LINK_COMMAND_WORD = "LINK";
    public static final String UNLINK_COMMAND_WORD = "UNLINK";
    public static final String HELP_COMMAND_WORD = "HELP";
    public static final String FIND_COMMAND_WORD = "FIND";
    public static final String BYE_COMMAND_WORD = "BYE";
    public static final String SCHEDULE_COMMAND_WORD = "SCHEDULE";
    public static final String REMINDER_COMMAND_WORD = "REMINDER";
    public static final String MATCH_COMMAND_WORD = "MATCH";
    public static final String EDIT_COMMAND_WORD = "EDIT";
    public static final String SHOW_COMMAND_WORD = "SHOW";

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
            "ADD", "LIST", "DONE", "DELETE", "HELP", "FIND", "BYE", "REMINDER", "SHOW",
            "SNOOZE", "SCHEDULE", "CHECK", "LINK", "UNLINK", "RENAME", "EDIT",
            "MATCH"
        };

        commandWord = SpellingErrorCorrector.commandCorrector(dict, commandWord);

        switch (commandWord) {
        case ADD_COMMAND_WORD:
            return AddCommandParser.parseAddCommand(arguments);
        case LIST_COMMAND_WORD:
            return ListCommandParser.parseListCommand(arguments);
        case DONE_COMMAND_WORD:
            return DoneCommandParser.parseDoneCommand(arguments);
        case SNOOZE_COMMAND_WORD:
            return SnoozeCommandParser.parseSnoozeCommand(arguments);
        case RENAME_COMMAND_WORD:
            return RenameCommandParser.parseRenameCommand(arguments);
        case LINK_COMMAND_WORD:
            return LinkCommandParser.parseLinkCommand(arguments);
        case UNLINK_COMMAND_WORD:
            return LinkCommandParser.parseUnlinkCommand(arguments);
        case DELETE_COMMAND_WORD:
            return DeleteCommandParser.parseDeleteCommand(arguments);
        case HELP_COMMAND_WORD:
            return HelpCommandParser.parseHelpCommand(arguments);
        case FIND_COMMAND_WORD:
            return FindCommandParser.parseFindCommand(arguments);
        case BYE_COMMAND_WORD:
            return ByeCommandParser.parseByeCommand(arguments);
        case SCHEDULE_COMMAND_WORD:
            return ScheduleCommandParser.parseScheduleCommand(arguments);
        case REMINDER_COMMAND_WORD:
            return ReminderCommandParser.parseReminder(arguments);
        case MATCH_COMMAND_WORD:
            return MatchCommandParser.parseMatch(arguments);
        case EDIT_COMMAND_WORD:
            return EditCommandParser.parseEditCommand(arguments);
        case SHOW_COMMAND_WORD:
            return ShowCommandParser.parseShowCommand(arguments);

        default:
            throw new DukeException("Command not found");
        }

    }
}

