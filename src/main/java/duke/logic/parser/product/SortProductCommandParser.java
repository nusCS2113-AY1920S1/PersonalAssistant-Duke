package duke.logic.parser.product;

import duke.logic.command.product.ListProductCommand;
import duke.logic.command.product.SortProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.message.ProductMessageUtils.MESSAGE_INVALID_CATEGORY;
import static duke.logic.message.ProductMessageUtils.MESSAGE_INVALID_SCOPE_VALUE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_SCOPE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_SORT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_SORT_REVERSE;

public class SortProductCommandParser implements Parser<SortProductCommand> {

    @Override
    public SortProductCommand parse(String args) throws ParseException {

        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
            PREFIX_PRODUCT_SORT,
            PREFIX_PRODUCT_SCOPE,
            PREFIX_PRODUCT_SORT_REVERSE);

        if (!map.getValue(PREFIX_PRODUCT_SORT).isPresent()) {
            throw new ParseException(MESSAGE_INVALID_CATEGORY);
        }

        String inputCat = map.getValue(PREFIX_PRODUCT_SORT).get();
        SortProductCommand.Category category;
        try {
            category = SortProductCommand.Category.valueOf(inputCat.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_CATEGORY);
        }

        ListProductCommand.Scope scope = ListProductCommand.Scope.ACTIVE;
        if (map.getValue(PREFIX_PRODUCT_SCOPE).isPresent()) {
            try {
                scope = ListProductCommand.Scope.valueOf(map.getValue(PREFIX_PRODUCT_SCOPE).get().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParseException(MESSAGE_INVALID_SCOPE_VALUE);
            }
        }

        boolean isIncreasing = !map.getValue(PREFIX_PRODUCT_SORT_REVERSE).isPresent();

        return new SortProductCommand(category, scope, isIncreasing);
    }
}
