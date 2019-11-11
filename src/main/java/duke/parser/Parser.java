package duke.parser;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.logic.commands.FindCommand;
import duke.logic.commands.AddLockerCommand;
import duke.logic.commands.AddBatchCommand;
import duke.logic.commands.AssignLockerCommand;
import duke.logic.commands.ByeCommand;
import duke.logic.commands.ClearCommand;
import duke.logic.commands.DeleteLockerCommand;
import duke.logic.commands.DeleteUsageCommand;
import duke.logic.commands.EditLockerCommand;
import duke.logic.commands.EditUsageCommand;
import duke.logic.commands.RemindersCommand;
import duke.logic.commands.SortCommand;
import duke.logic.commands.ExportLockerCommand;
import duke.logic.commands.ExportLockerSelectCommand;
import duke.logic.commands.HelpCommand;
import duke.logic.commands.ListCommand;
import duke.logic.commands.SelectLockerCommand;
import duke.logic.commands.UndoCommand;
import duke.logic.commands.RedoCommand;
import duke.logic.commands.HistoryCommand;

import duke.logic.commands.Command;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

/**
 * Parses user input.
 */
public class Parser {

    //separates user input into command word and arguments.
    private static final Pattern GENERAL_COMMAND_FORMAT =
            Pattern.compile("(?<commandType>\\S+)(?<arguments>.*)");

    private static final String COMMAND_TYPE = "commandType";
    private static final String ARGUMENTS = "arguments";
    private static final String INVALID_FORMAT = " The command entered has invalid format. "
            + "Type help to check all the commands available in SpongeBob";
    private static final String STATS_COMMAND = "stats";
    private static final Logger logger = Log.getLogger();
    private static final String PARSING = "Parsing ";

    /**
     * Parses the command entered by the user.
     * @param fullCommand stores the command entered by the user
     * @return objects of type Command depending on the command given by the user
     * @throws DukeException if the user inputs invalid command i.e in a an unexpected format
     */
    public Command parse(String fullCommand) throws DukeException {
        logger.log(Level.INFO, PARSING + fullCommand);
        requireNonNull(fullCommand);

        Matcher commandMatch = GENERAL_COMMAND_FORMAT.matcher(fullCommand.trim());
        if (!commandMatch.matches()) {
            throw new DukeException(INVALID_FORMAT);
        }
        String commandType = commandMatch.group(COMMAND_TYPE);
        String arguments = commandMatch.group(ARGUMENTS);
        switch (commandType.toLowerCase()) {
        case AddLockerCommand.COMMAND_WORD:
            return new AddLockerCommandParser().parse(arguments);
        case AddBatchCommand.COMMAND_WORD:
            return new AddBatchCommandParser().parse(arguments);
        case AssignLockerCommand.COMMAND_WORD:
            return new AssignLockerCommandParser().parse(arguments);
        case DeleteLockerCommand.COMMAND_WORD:
            return new DeleteLockerCommandParser().parse(arguments);
        case DeleteUsageCommand.COMMAND_WORD:
            return new DeleteUsageCommandParser().parse(arguments);
        case EditLockerCommand.COMMAND_WORD:
            return new EditLockerCommandParser().parse(arguments);
        case EditUsageCommand.COMMAND_WORD:
            return new EditUsageParser().parse(arguments);
        case SelectLockerCommand.COMMAND_WORD:
            return new SelectLockerCommandParser().parse(arguments);
        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(fullCommand);
        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(fullCommand);
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand();
        case ExportLockerCommand.COMMAND_WORD:
            return new ExportLockerCommand();
        case ExportLockerSelectCommand.COMMAND_WORD:
            return new ExportLockerSelectCommandParser().parse(arguments);
        case STATS_COMMAND:
            return new StatsCommandParser().parse();
        case RemindersCommand.COMMAND_WORD:
            return new RemindersCommand();
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        default:
            throw new DukeException(INVALID_FORMAT);
        }
    }
}
