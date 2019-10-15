package duke.logic.parser.commons;

import duke.commons.core.Message;
import duke.logic.command.Command;
import duke.logic.command.RedoCommand;
import duke.logic.command.UndoCommand;
import duke.logic.command.inventory.InventoryCommand;
import duke.logic.command.order.OrderCommand;
import duke.logic.command.product.ProductCommand;
import duke.logic.command.sale.SaleCommand;
import duke.logic.command.shortcut.ExecuteShortcutCommand;
import duke.logic.command.shortcut.SetShortcutCommand;
import duke.logic.parser.exceptions.ParseException;
import duke.logic.parser.inventory.InventoryCommandParser;
import duke.logic.parser.order.OrderCommandParser;
import duke.logic.parser.product.ProductCommandParser;
import duke.logic.parser.sale.SaleCommandParser;
import duke.logic.parser.shortcut.ExecuteShortcutCommandParser;
import duke.logic.parser.shortcut.SetShortcutCommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser that parses user input into {@code Command}.
 */
public class BakingHomeParser {

    /**
     * Used to get primary command word.
     * Capture group 1: primary command word.
     * Capture group 2: (optional) sub-command word and args.
     */
    private static final Pattern PRIMARY_COMMAND_FORMAT = Pattern.compile("^(\\w+)\\s*(.+)?");

    /**
     * Parses user input into {@code Command}.
     *
     * @param userInput full input text from user
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {

        final Matcher matcher = PRIMARY_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(Message.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        String primaryCommand = matcher.group(1);
        String subCommandAndArgs = matcher.group(2);
        if (subCommandAndArgs == null) {
            subCommandAndArgs = "";
        }

        switch (primaryCommand) {
        case OrderCommand.COMMAND_WORD:
            return new OrderCommandParser().parse(subCommandAndArgs);
        case SaleCommand.COMMAND_WORD:
            return new SaleCommandParser().parse(subCommandAndArgs);
        case ProductCommand.COMMAND_WORD:
            return new ProductCommandParser().parse(subCommandAndArgs);
        case InventoryCommand.COMMAND_WORD:
            return new InventoryCommandParser().parse(subCommandAndArgs);
        case SetShortcutCommand.COMMAND_WORD:
            return new SetShortcutCommandParser().parse(subCommandAndArgs);
        case ExecuteShortcutCommand.COMMAND_WORD:
            return new ExecuteShortcutCommandParser().parse(subCommandAndArgs);
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
        default:
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
