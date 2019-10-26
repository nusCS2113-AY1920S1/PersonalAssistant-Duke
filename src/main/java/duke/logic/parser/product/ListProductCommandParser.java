package duke.logic.parser.product;

import duke.logic.command.product.ListProductCommand;
import duke.logic.message.ProductMessage;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_SCOPE;

public class ListProductCommandParser implements Parser<ListProductCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ListProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_SCOPE);
        String status = map.getValue(PREFIX_PRODUCT_SCOPE).orElse("active");
        ListProductCommand.Scope scope;
        try {
            scope = ListProductCommand.Scope.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(ProductMessage.MESSAGE_INVALID_SCOPE_VALUE);
        }
        return new ListProductCommand(scope);
    }
}
