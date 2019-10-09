package duke.logic.parser.product;

import duke.commons.core.Message;
import duke.logic.command.product.AddProductCommand;
import duke.logic.command.product.ProductCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;
import duke.logic.parser.order.AddOrderCommandParser;

public class ProductCommandParser implements SubCommandParser<ProductCommand> {

    @Override
    public ProductCommand parse(String subCommandAndArgs) throws ParseException {
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
        case AddProductCommand.COMMAND_WORD:
            return new AddProductCommandParser().parse(args);
    //    case EditOrderCommand.COMMAND_WORD:
    //        return new EditOrderCommandParser().parse(args);
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }

}
