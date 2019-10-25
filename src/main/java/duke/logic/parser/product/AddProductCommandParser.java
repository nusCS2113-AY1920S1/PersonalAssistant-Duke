package duke.logic.parser.product;

import duke.logic.command.product.AddProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_STATUS;

public class AddProductCommandParser implements Parser<AddProductCommand> {

    public static final String MESSAGE_MISSING_PRODUCT_NAME = "Product Name must be present";

    private ArgumentMultimap map;

    @Override
    public AddProductCommand parse(String args) throws ParseException {
        getMap(args);
        Product product = getProductFromMap();
        return new AddProductCommand(product);
    }

    private void getMap(String args) {
        map = ArgumentTokenizer.tokenize(args,
                PREFIX_PRODUCT_NAME,
                PREFIX_PRODUCT_INGREDIENT,
                PREFIX_PRODUCT_INGREDIENT_COST,
                PREFIX_PRODUCT_RETAIL_PRICE
        );
    }

    /** Gets data from user input */
    private Product getProductFromMap() {
        Product product = new Product();
        if (map.getValue(PREFIX_PRODUCT_NAME).isPresent()) {
            product.setProductName(map.getValue(PREFIX_PRODUCT_NAME).get());
        } else {
            throw new ParseException(MESSAGE_MISSING_PRODUCT_NAME);
        }
        if (map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).isPresent()) {
            product.setRetailPrice(Double.parseDouble(map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).get()));
        }
        if (map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).isPresent()) {
            product.setIngredientCost(Double.parseDouble(map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).get()));
        }
        if (map.getValue(PREFIX_PRODUCT_STATUS).isPresent()) {
            product.setStatus(Product.Status.valueOf(
                    map.getValue(PREFIX_PRODUCT_STATUS).get().toUpperCase())
            );
        }
        if (map.getValue(PREFIX_PRODUCT_INGREDIENT).isPresent()) {
            IngredientItemList ingredientItemList =
                    IngredientItemListParser.getIngredientsInInput(
                            map.getValue(PREFIX_PRODUCT_INGREDIENT).orElse("")
                    );
            product.setIngredients(ingredientItemList);
        }
        return product;
    }
}
