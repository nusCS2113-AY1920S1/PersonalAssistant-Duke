package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.AddBatchCommand;
import duke.logic.commands.AddLockerCommand;
import duke.logic.commands.AssignLockerCommand;
import duke.logic.commands.ByeCommand;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteLockerCommand;
import duke.logic.commands.DeleteUsageCommand;
import duke.logic.commands.EditLockerCommand;
import duke.logic.commands.EditUsageCommand;
import duke.logic.commands.ExportLockerCommand;
import duke.logic.commands.HelpCommand;
import duke.logic.commands.ListCommand;

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

    /**
     * Parses the command entered by the user.
     * @param fullCommand stores the command entered by the user
     * @return objects of type Command depending on the command given by the user
     * @throws DukeException if the user inputs invalid command i.e in a an unexpected format
     */
    public Command parse(String fullCommand) throws DukeException {
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
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case ByeCommand.COMMAND_WORD:
            return new ByeCommand();
        case ExportLockerCommand.COMMAND_WORD:
            return new ExportLockerCommand();
        case STATS_COMMAND:
            return new StatsCommandParser().parse();
        default:
            throw new DukeException(INVALID_FORMAT);
        }
    }
}

