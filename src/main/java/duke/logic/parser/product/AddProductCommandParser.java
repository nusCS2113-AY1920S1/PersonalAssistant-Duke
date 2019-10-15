package duke.logic.parser.product;

import duke.logic.command.product.AddProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;


public class AddProductCommandParser implements Parser<AddProductCommand> {

    public static final Double DEFAULT_PRODUCT_RETAIL_PRICE = 0.0;
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public AddProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_PRODUCT_NAME,
                PREFIX_PRODUCT_INGREDIENT,
                PREFIX_PRODUCT_INGREDIENT_COST,
                PREFIX_PRODUCT_RETAIL_PRICE
        );

        if (map.getValue(PREFIX_CUSTOMER_NAME).isEmpty() || map.getValue(PREFIX_PRODUCT_NAME) == null) {
            throw new ParseException("Please enter the name of the product");
        }

        IngredientItemList ingredientItemList =
                IngredientItemListParser.getIngredientItemList(
                        map.getValue(PREFIX_PRODUCT_INGREDIENT).orElse("")
                );
        Product product = new Product(
                map.getValue(PREFIX_CUSTOMER_NAME).orElse("ProductName"),
                map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).orElse(String.valueOf(DEFAULT_PRODUCT_RETAIL_PRICE)),
                map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).orElse(String.valueOf(0)),
                ingredientItemList
        );
        System.out.println(product.toString());
        return new AddProductCommand(product);
    }
}
