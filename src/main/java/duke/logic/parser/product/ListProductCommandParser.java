package duke.logic.parser.product;

import duke.logic.command.product.ListProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.commons.Prefix;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_STATUS;

public class ListProductCommandParser implements Parser<ListProductCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ListProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_STATUS);
        String status = map.getValue(PREFIX_PRODUCT_STATUS).orElse("active");
        ListProductCommand.Scope scope = ListProductCommand.Scope.valueOf(status.toUpperCase());
        return new ListProductCommand(scope);
    }
}
