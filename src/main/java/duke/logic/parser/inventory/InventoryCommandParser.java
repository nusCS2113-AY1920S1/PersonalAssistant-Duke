package duke.logic.parser.inventory;

import duke.commons.core.Message;
import duke.logic.command.inventory.AddInventoryCommand;
import duke.logic.command.inventory.DeleteInventoryCommand;
import duke.logic.command.inventory.EditInventoryCommand;
import duke.logic.command.inventory.InventoryCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

public class InventoryCommandParser implements SubCommandParser<InventoryCommand> {

    @Override
    public InventoryCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
        case AddInventoryCommand.COMMAND_WORD:
            return new AddInventoryCommandParser().parse(args);
        case DeleteInventoryCommand.COMMAND_WORD:
            return new DeleteInventoryCommandParser().parse(args);
        case EditInventoryCommand.COMMAND_WORD:
            return new EditInventoryCommandParser().parse(args);
        default:
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
