package duke.logic.parser.product;

import duke.commons.core.LogsCenter;
import duke.logic.command.product.ProductDescriptor;
import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.exceptions.ParseException;
import duke.model.product.Product;

import java.util.logging.Logger;

import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_STATUS;

public class ProductParserUtil {

    private static final Logger logger = LogsCenter.getLogger(ProductParserUtil.class);


    public static ProductDescriptor createProductDescriptor(ArgumentMultimap map) {
        ProductDescriptor productDescriptor = new ProductDescriptor();

        if (map.getValue(PREFIX_PRODUCT_NAME).isPresent()) {
            String name = map.getValue(PREFIX_PRODUCT_NAME).get();
            if (name.isBlank() || name.isEmpty()) {
                logger.info(ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);
                throw new ParseException(ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);
            }
            productDescriptor.setProductName(name);
        }

        try {
            if (map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).isPresent()) {
                productDescriptor.setRetailPrice(Double.parseDouble(map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).get()));
            }
            if (map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).isPresent()) {
                productDescriptor.setIngredientCost(Double.parseDouble(map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).get()));
            }
        } catch (NumberFormatException e) {
            throw new ParseException(ProductMessageUtils.MESSAGE_INVALID_NUMBER_FORMAT);
        }

        if (map.getValue(PREFIX_PRODUCT_STATUS).isPresent()) {
            try {
                productDescriptor.setStatus(Product.Status.valueOf(
                    map.getValue(PREFIX_PRODUCT_STATUS).get().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ParseException(ProductMessageUtils.MESSAGE_INVALID_STATUS_VALUE);
            }
        }
        if (map.getValue(PREFIX_PRODUCT_INGREDIENT).isPresent()) {
            String input = map.getValue(PREFIX_PRODUCT_INGREDIENT).orElse("");
            productDescriptor.setIngredientItemList(IngredientItemListParser.getIngredientsInInput(input));
        }
        return productDescriptor;
    }
}
