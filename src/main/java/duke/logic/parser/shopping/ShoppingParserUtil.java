package duke.logic.parser.shopping;

import duke.logic.command.shopping.ShoppingDescriptor;
import duke.logic.parser.commons.ArgumentMultimap;

import static duke.logic.parser.commons.CliSyntax.*;

public class ShoppingParserUtil {
    public static ShoppingDescriptor createShoppingDescriptor(ArgumentMultimap map) {
        ShoppingDescriptor shoppingDescriptor = new ShoppingDescriptor();

        if (map.getValue(PREFIX_SHOPPING_NAME).isPresent()) {
            shoppingDescriptor.setName(map.getValue(PREFIX_SHOPPING_NAME).get());
        }
        if (map.getValue(PREFIX_SHOPPING_QUANTITY).isPresent()) {
            shoppingDescriptor.setQuantity(Double.parseDouble(map.getValue(PREFIX_SHOPPING_QUANTITY).get()));
        }
        if (map.getValue(PREFIX_SHOPPING_REMARKS).isPresent()) {
            shoppingDescriptor.setRemarks(map.getValue(PREFIX_SHOPPING_REMARKS).get());
        }
        if (map.getValue(PREFIX_SHOPPING_COST).isPresent()) {
            shoppingDescriptor.setUnitCost(Double.parseDouble(map.getValue(PREFIX_SHOPPING_COST).get()));
        }

        return shoppingDescriptor;
    }
}