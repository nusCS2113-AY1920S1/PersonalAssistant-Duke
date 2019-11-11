package duke.logic.parser.product;

import duke.commons.core.Message;
import duke.logic.command.product.SwitchProductPageCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code SwitchProductCommand}.
 */
public class SwitchProductPageCommandParser implements Parser<SwitchProductPageCommand> {

    @Override
    public SwitchProductPageCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new SwitchProductPageCommand();
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }
}
