package duke.logic.parser.product;

import duke.commons.core.Message;
import duke.logic.command.product.AddProductCommand;
import duke.logic.command.product.EditProductCommand;
import duke.logic.command.product.ListProductCommand;
import duke.logic.command.product.ProductCommand;
import duke.logic.command.product.ShowProductPageCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;


public class ProductCommandParser implements SubCommandParser<ProductCommand> {

    @Override
    public ProductCommand parse(String subCommandAndArgs) throws ParseException {
        if (subCommandAndArgs.equals("")) {
            return new ShowProductPageCommand();
        }
        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
        case AddProductCommand.COMMAND_WORD:
            return new AddProductCommandParser().parse(args);
        case EditProductCommand.COMMAND_WORD:
            return new EditProductCommandParser().parse(args);
        case ListProductCommand.COMMAND_WORD:
            return new ListProductCommandParser().parse(args);
        }
        throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
    }

}
