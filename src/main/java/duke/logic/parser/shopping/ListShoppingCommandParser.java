package duke.logic.parser.shopping;

import duke.commons.core.Message;
import duke.logic.command.shopping.ListShoppingCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code ListShoppingCommand}.
 */
public class ListShoppingCommandParser implements Parser<ListShoppingCommand> {

    @Override
    public ListShoppingCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new ListShoppingCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
