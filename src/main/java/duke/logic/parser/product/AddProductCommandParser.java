package duke.logic.parser.product;

import duke.logic.command.product.AddProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;
import static duke.logic.parser.product.ProductParserUtil.createProductDescriptor;

/**
 * A parser that parses {@code AddProductCommand}.
 */
public class AddProductCommandParser implements Parser<AddProductCommand> {

    private ArgumentMultimap map;

    @Override
    public AddProductCommand parse(String args) throws ParseException {
        map = ArgumentTokenizer.tokenize(args,
            PREFIX_PRODUCT_NAME,
            PREFIX_PRODUCT_INGREDIENT,
            PREFIX_PRODUCT_INGREDIENT_COST,
            PREFIX_PRODUCT_RETAIL_PRICE
        );
        return new AddProductCommand(createProductDescriptor(map));
    }
}

