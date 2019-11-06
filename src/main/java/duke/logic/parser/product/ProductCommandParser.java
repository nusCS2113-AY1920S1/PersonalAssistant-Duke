package duke.logic.parser.product;

import duke.commons.core.Message;
import duke.logic.command.product.AddProductCommand;
import duke.logic.command.product.DeleteProductCommand;
import duke.logic.command.product.EditProductCommand;
import duke.logic.command.product.ListProductCommand;
import duke.logic.command.product.ProductCommand;
import duke.logic.command.product.SearchProductCommand;
import duke.logic.command.product.ShowProductCommand;
import duke.logic.command.product.SortProductCommand;
import duke.logic.command.product.SwitchProductPageCommand;
import duke.logic.parser.commons.SubCommandParser;
import duke.logic.parser.exceptions.ParseException;


public class


ProductCommandParser implements SubCommandParser<ProductCommand> {

    @Override
    public ProductCommand parse(String subCommandAndArgs) throws ParseException {

        String subCommand = SubCommandParser.getSubCommandWord(subCommandAndArgs);
        String args = SubCommandParser.getArgs(subCommandAndArgs);

        switch (subCommand) {
        case SwitchProductPageCommand.COMMAND_WORD:
            return new SwitchProductPageCommandParser().parse(args);
        case ShowProductCommand.COMMAND_WORD:
            return new ShowProductCommandParser().parse(args);
        case AddProductCommand.COMMAND_WORD:
            return new AddProductCommandParser().parse(args);
        case EditProductCommand.COMMAND_WORD:
            return new EditProductCommandParser().parse(args);
        case ListProductCommand.COMMAND_WORD:
            return new ListProductCommandParser().parse(args);
        case DeleteProductCommand.COMMAND_WORD:
            return new DeleteProductCommandParser().parse(args);
        case SearchProductCommand.COMMAND_WORD:
            return new SearchProductCommandParser().parse(args);
        case SortProductCommand.COMMAND_WORD:
            return new SortProductCommandParser().parse(args);
        default:
            throw new ParseException(Message.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
