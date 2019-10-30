package duke.logic.parser.product;

import duke.logic.command.product.SwitchProductPageCommand;
import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

public class SwitchProductPageCommandParser implements Parser<SwitchProductPageCommand> {

    @Override
    public SwitchProductPageCommand parse(String args) throws ParseException {
        if ("".equals(args)) {
            return new SwitchProductPageCommand();
        }
        throw new ParseException(ProductMessageUtils.MESSAGE_UNRECOGNIZED_COMMAND);
    }
}
