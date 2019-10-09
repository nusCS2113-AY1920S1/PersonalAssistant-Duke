package duke.logic.parser.inventory;

import duke.commons.core.Message;
import duke.logic.command.inventory.AddInventoryCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;

public class InventoryCommandParser implements SubCommandParser<AddInventoryCommand> {

    @Override
    public AddInventoryCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
            case AddInventoryCommand.COMMAND_WORD:
                return new AddInventoryCommandParser().parse(args);
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
