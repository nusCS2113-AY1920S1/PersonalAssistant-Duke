package duke.logic.parser.inventory;

import duke.commons.core.Message;
import duke.logic.command.inventory.ShowInventoryCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code ShowInventoryCommand}.
 */
public class ShowInventoryCommandParser implements Parser<ShowInventoryCommand> {

    @Override
    public ShowInventoryCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new ShowInventoryCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
