package duke.logic.parser.product;

import duke.logic.command.product.AddProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.commons.Ingredient;
import duke.model.product.Product;
import duke.model.ingredient.IngredientList;

import static duke.logic.parser.commons.CliSyntax.*;

public class AddProductCommandParser implements Parser<AddProductCommand> {
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
                PREFIX_PRODUCT_COST,
                PREFIX_PRODUCT_PRICE
        );

        IngredientList ingredients = new IngredientList(){};


        Product product = new Product(
                map.getValue(PREFIX_CUSTOMER_NAME).orElse("ProductName"),
                map.getValue(PREFIX_PRODUCT_PRICE).orElse(String.valueOf(0)),
                map.getValue(PREFIX_PRODUCT_COST).orElse(String.valueOf(0))
        );
        return new AddProductCommand(product);
    }

    //Todo: IngredientList Parser -ingt [ingredient_name, qty] [ingredient_name2, qty] [ingredient_name3]
    private IngredientList ingredientListParser() {
        return new IngredientList() {};
    }
}
