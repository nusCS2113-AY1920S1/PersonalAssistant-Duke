package duke.parser.inventory;

import duke.command.inventory.AddInventoryCommand;
import duke.parser.SubCommandParser;
import duke.parser.exceptions.ParseException;

public class InventoryCommandParser implements SubCommandParser<AddInventoryCommand> {

    @Override
    public AddInventoryCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
            case AddInventoryCommand.COMMAND_WORD:
                return new AddInventoryCommandParser().parse(args);
        }
        return null;
    }
}
