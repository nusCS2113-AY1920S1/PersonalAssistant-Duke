package duke.logic.parser.shopping;

import duke.commons.core.Message;
import duke.logic.command.shopping.ShowShoppingCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code ShowShoppingCommand}.
 */
public class ShowShoppingCommandParser implements Parser<ShowShoppingCommand> {

    @Override
    public ShowShoppingCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new ShowShoppingCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
