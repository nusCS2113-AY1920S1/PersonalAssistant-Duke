package duke.logic.parser.shopping;

import duke.commons.core.Message;
import duke.logic.command.shopping.ShowShoppingCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

public class ShowShoppingCommandParser implements Parser<ShowShoppingCommand> {

    @Override
    public ShowShoppingCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new ShowShoppingCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
