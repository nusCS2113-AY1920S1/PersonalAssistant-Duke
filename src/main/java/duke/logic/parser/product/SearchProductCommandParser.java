package duke.logic.parser.product;

import duke.logic.command.product.SearchProductCommand;
import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_SEARCH;

/**
 * A parser that parses {@code SearchProductCommand}.
 */
public class SearchProductCommandParser implements Parser<SearchProductCommand> {

    private ArgumentMultimap map;

    @Override
    public SearchProductCommand parse(String args) throws ParseException {
        map = ArgumentTokenizer.tokenize(args,
            PREFIX_PRODUCT_SEARCH
        );
        String keyword;
        if (!map.getValue(PREFIX_PRODUCT_SEARCH).isPresent()) {
            throw new ParseException(ProductMessageUtils.MESSAGE_MISSING_SEARCH_PREFIX);
        }
        keyword = map.getValue(PREFIX_PRODUCT_SEARCH).get().toLowerCase();
        if (keyword.isEmpty() || keyword.isBlank()) {
            throw new ParseException(ProductMessageUtils.MESSAGE_MISSING_KEYWORD);
        }
        return new SearchProductCommand(keyword);
    }
}
