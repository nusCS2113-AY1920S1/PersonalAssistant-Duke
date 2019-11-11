package duke.logic.parser.inventory;

import duke.commons.core.Message;
import duke.logic.command.inventory.ClearInventoryCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code ClearInventoryCommand}.
 */
public class ClearInventoryCommandParser implements Parser<ClearInventoryCommand> {
    @Override
    public ClearInventoryCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new ClearInventoryCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
