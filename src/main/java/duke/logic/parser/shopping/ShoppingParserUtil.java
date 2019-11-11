package duke.logic.parser.shopping;

import duke.logic.command.shopping.ShoppingDescriptor;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_QUANTITY;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_REMARKS;

/**
 * A Utility class for the Shopping list parser
 */
public class ShoppingParserUtil {

    private static final String MESSAGE_VALID_QUANTITY = "Please enter a valid quantity, must only contain numbers";
    private static final String MESSAGE_VALID_COST = "Please enter a valid cost, must only contain numbers";

    /**
     * Creates a ShoppingDescriptor constructor with edited values.
     *
     * @param map Map of all the arguments of the sub commands
     * @return ShoppingDescriptor with edited values
     */
    public static ShoppingDescriptor createShoppingDescriptor(ArgumentMultimap map) {
        ShoppingDescriptor shoppingDescriptor = new ShoppingDescriptor();

        if (map.getValue(PREFIX_SHOPPING_NAME).isPresent()) {
            shoppingDescriptor.setName(map.getValue(PREFIX_SHOPPING_NAME).get());
        }
        if (map.getValue(PREFIX_SHOPPING_QUANTITY).isPresent()) {
            checkValidQuantity(map.getValue(PREFIX_SHOPPING_QUANTITY).get());
            shoppingDescriptor.setQuantity(Double.parseDouble(map.getValue(PREFIX_SHOPPING_QUANTITY).get()));
        }
        if (map.getValue(PREFIX_SHOPPING_REMARKS).isPresent()) {
            shoppingDescriptor.setRemarks(map.getValue(PREFIX_SHOPPING_REMARKS).get());
        }
        if (map.getValue(PREFIX_SHOPPING_COST).isPresent()) {
            checkValidCost(map.getValue(PREFIX_SHOPPING_COST).get());
            shoppingDescriptor.setUnitCost(Double.parseDouble(map.getValue(PREFIX_SHOPPING_COST).get()));
        }

        return shoppingDescriptor;
    }

    /**
     * Checks if the quantity inputted by the user is a valid double.
     */
    public static void checkValidQuantity(String quantityInput) throws ParseException {
        try {
            Double.parseDouble(quantityInput);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_VALID_QUANTITY);
        }
    }

    /**
     * Checks if the cost inputted by the user is a valid double.
     */
    public static void checkValidCost(String costInput) throws ParseException {
        try {
            Double.parseDouble(costInput);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_VALID_COST);
        }
    }
}