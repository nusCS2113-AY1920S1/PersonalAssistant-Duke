package duke.logic.parser.shopping;

import duke.commons.core.Message;
import duke.logic.command.shopping.ClearShoppingCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code ClearShoppingCommand}.
 */
public class ClearShoppingCommandParser implements Parser<ClearShoppingCommand> {

    @Override
    public ClearShoppingCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new ClearShoppingCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
