package duke.logic.parser.product;

import duke.logic.command.product.AddProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_STATUS;
import static duke.logic.parser.product.IngredientItemListParser.getIngredientsInInput;

public class AddProductCommandParser implements Parser<AddProductCommand> {

    public static final Double DEFAULT_PRODUCT_RETAIL_PRICE = 0.0;

    private ArgumentMultimap map;

  //  private Product product;


    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public AddProductCommand parse(String args) throws ParseException {

         map = ArgumentTokenizer.tokenize(args,
            PREFIX_PRODUCT_NAME,
            PREFIX_PRODUCT_INGREDIENT,
            PREFIX_PRODUCT_INGREDIENT_COST,
            PREFIX_PRODUCT_RETAIL_PRICE
        );


      //  return new AddProductCommand(toAdd);
        if (map.getValue(PREFIX_CUSTOMER_NAME).isEmpty() || map.getValue(PREFIX_PRODUCT_NAME) == null) {
            throw new ParseException("Please enter the name of the product");
        }

        IngredientItemList ingredientItemList =
                IngredientItemListParser.getIngredientsInInput(
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

    /** Gets data from user input */
    private Product getNewProductInfoFromMap() {
        Product product = new Product();
        if (map.getValue(PREFIX_PRODUCT_NAME).isPresent()) {
            product.setProductName(map.getValue(PREFIX_PRODUCT_NAME).get());
        } else {
            throw new ParseException("Product Name must be present");
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
            product.setIngredients(getIngredientsInInput(map.getValue(PREFIX_PRODUCT_INGREDIENT).orElse(
                    "")));
        }
        return product;
    }
}
