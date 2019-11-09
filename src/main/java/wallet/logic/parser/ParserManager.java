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
import wallet.logic.command.GenerateCommand;
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

    private String parametersError = " command currently has no parameters!";
    /**
     * Parses the user input command and returns the corresponding Command object.
     *
     * @param fullCommand The input of user.
     * @return The corresponding Command object.
     */
    public Command parseCommand(String fullCommand) throws ParseException {
        String[] arguments = fullCommand.split(" ", 2);

        switch (arguments[0].toLowerCase()) {
        case AddCommand.COMMAND_WORD:
            try{
                return new AddCommandParser().parse(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(AddCommand.COMMAND_WORD + parametersError);
            }

        case EditCommand.COMMAND_WORD:
            try {
                return new EditCommandParser().parse(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(EditCommand.COMMAND_WORD + parametersError);
            }

        case ListCommand.COMMAND_WORD:
            try {
                return new ListCommandParser().parse(arguments[1].toLowerCase());
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(ListCommand.COMMAND_WORD + parametersError);
            }

        case ViewCommand.COMMAND_WORD:
            try {
                return new ViewCommandParser().parse(arguments[1].toLowerCase());
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(ViewCommand.COMMAND_WORD + parametersError);
            }

        case SetBudgetCommand.COMMAND_WORD:
            try {
                return new SetBudgetParser().parse(arguments[1].toLowerCase());
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(SetBudgetCommand.COMMAND_WORD + parametersError);
            }

        case DeleteCommand.COMMAND_WORD:
            try {
                return new DeleteCommandParser().parse(arguments[1].toLowerCase());
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(DeleteCommand.COMMAND_WORD + parametersError);
            }

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ReminderCommand.COMMAND_WORD:
            try {
                return new ReminderCommandParser().parse(arguments[1].toLowerCase());
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(ReminderCommand.COMMAND_WORD + parametersError);
            }

        case DoneCommand.COMMAND_WORD:
            try {
                return new DoneCommandParser().parse(arguments[1].toLowerCase());
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(DoneCommand.COMMAND_WORD + parametersError);
            }

        case ExportCommand.COMMAND_WORD:
            try {
                return new ExportCommandParser().parse(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(ExportCommand.COMMAND_WORD + parametersError);
            }

        case ImportCommand.COMMAND_WORD:
            try {
                return new ImportCommandParser().parse(arguments[1]);
            }  catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(ImportCommand.COMMAND_WORD + parametersError);
            }

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand(arguments[1]);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case GenerateCommand.COMMAND_WORD:
            return new GenerateCommand();

        case CurrencyCommand.COMMAND_WORD:
            try {
                return new CurrencyParser().parse(arguments[1].toLowerCase());
            } catch (ArrayIndexOutOfBoundsException err) {
                throw new InsufficientParameters(CurrencyCommand.COMMAND_WORD + parametersError);
            }

        default:
            return null;
        }
    }
}
