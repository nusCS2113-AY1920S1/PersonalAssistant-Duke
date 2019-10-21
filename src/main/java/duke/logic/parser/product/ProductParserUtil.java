package duke.logic.parser.product;

import duke.logic.command.product.ProductDescriptor;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.model.product.Product;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_STATUS;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;

public class ProductParserUtil {

    public static ProductDescriptor createProductDescriptor(ArgumentMultimap map) {
        ProductDescriptor productDescriptor = new ProductDescriptor();

        if (map.getValue(PREFIX_PRODUCT_NAME).isPresent()) {
            productDescriptor.setProductName(map.getValue(PREFIX_PRODUCT_NAME).get());
        }
        if (map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).isPresent()) {
            productDescriptor.setRetailPrice(Double.parseDouble(map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).get()));
        }
        if (map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).isPresent()) {
            productDescriptor.setIngredientCost(Double.parseDouble(map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).get()));
        }
        if (map.getValue(PREFIX_PRODUCT_STATUS).isPresent()) {
            productDescriptor.setStatus(Product.Status.valueOf(
                    map.getValue(PREFIX_PRODUCT_STATUS).get().toUpperCase())
            );
        }
        if (map.getValue(PREFIX_PRODUCT_INGREDIENT).isPresent()) {
            String input = map.getValue(PREFIX_PRODUCT_INGREDIENT).orElse("");
            productDescriptor.setIngredientItemList(IngredientItemListParser.getIngredientsInInput(input));
        }
        return productDescriptor;
    }
}
