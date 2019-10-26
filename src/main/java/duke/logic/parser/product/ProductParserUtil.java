package duke.logic.parser.product;

import duke.logic.command.product.ProductDescriptor;
import duke.logic.command.product.ProductMessage;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.exceptions.ParseException;
import duke.model.product.Product;
import org.ocpsoft.prettytime.shade.net.fortuna.ical4j.data.ParserException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_STATUS;

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
            try {
                productDescriptor.setStatus(Product.Status.valueOf(
                        map.getValue(PREFIX_PRODUCT_STATUS).get().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ParseException(ProductMessage.MESSAGE_INVALID_STATUS_VALUE);
            }
        }
        if (map.getValue(PREFIX_PRODUCT_INGREDIENT).isPresent()) {
            String input = map.getValue(PREFIX_PRODUCT_INGREDIENT).orElse("");
            productDescriptor.setIngredientItemList(IngredientItemListParser.getIngredientsInInput(input));
        }
        return productDescriptor;
    }
}
