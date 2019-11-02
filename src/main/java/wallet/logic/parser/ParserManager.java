package wallet.logic.parser;

import wallet.exception.InsufficientParameters;
import wallet.logic.command.AddCommand;
import wallet.logic.command.Command;
import wallet.logic.command.CurrencyCommand;
import wallet.logic.command.DeleteCommand;
import wallet.logic.command.DoneCommand;
import wallet.logic.command.EditCommand;
import wallet.logic.command.ExitCommand;
import wallet.logic.command.ExportCommand;
import wallet.logic.command.HelpCommand;
import wallet.logic.command.HistoryCommand;
import wallet.logic.command.ImportCommand;
import wallet.logic.command.ListCommand;
import wallet.logic.command.RedoCommand;
import wallet.logic.command.ReminderCommand;
import wallet.logic.command.SetBudgetCommand;
import wallet.logic.command.UndoCommand;
import wallet.logic.command.ViewCommand;

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
            try {
                return new ViewCommandParser().parse(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters("view command currently has no parameters!");
            }

        case SetBudgetCommand.COMMAND_WORD:
            try {
                return new SetBudgetParser().parse(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters("budget command currently has no parameters!");
            }

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments[1]);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments[1]);

        case DoneCommand.COMMAND_WORD:
            try {
                return new DoneCommandParser().parse(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters("done command currently has no parameters!");
            }

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

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case CurrencyCommand.COMMAND_WORD:
            try {
                return new CurrencyParser().parse(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters("currency command currently has no parameters!");
            }

        default:
            return null;
        }
    }
}
