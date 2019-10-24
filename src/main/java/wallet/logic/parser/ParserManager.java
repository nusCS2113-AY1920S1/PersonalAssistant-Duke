package wallet.logic.parser;

import wallet.logic.command.*;

import java.text.ParseException;


/**
 * The ParserManager Class that handles all CommandParser classes.
 */
public class ParserManager {

    /**
     * Parses the user input command and returns the corresponding Command object.
     * @param fullCommand The input of user.
     * @return The corresponding Command object.
     */
    public Command parseCommand(String fullCommand) throws ParseException {
        String[] arguments = fullCommand.split(" ", 2);

        switch (arguments[0]) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments[1]);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments[1]);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments[1]);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments[1]);

        case SetBudgetCommand.COMMAND_WORD:
            return new SetBudgetParser().parse(arguments[1]);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments[1]);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments[1]);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments[1]);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments[1]);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments[1]);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        default:
            return null;
        }
    }
}
