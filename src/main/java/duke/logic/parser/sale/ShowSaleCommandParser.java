package duke.logic.parser.sale;

import duke.commons.core.Message;
import duke.logic.command.sale.ShowSaleCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code ShowSaleCommand}.
 */
public class ShowSaleCommandParser implements Parser<ShowSaleCommand> {
    @Override
    public ShowSaleCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new ShowSaleCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}