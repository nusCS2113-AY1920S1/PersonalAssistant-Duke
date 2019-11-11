package duke.logic.parser.inventory;

import duke.logic.command.inventory.InventoryDescriptor;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.exceptions.ParseException;

import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_QUANTITY;
import static duke.logic.parser.commons.CliSyntax.PREFIX_INVENTORY_REMARKS;

/**
 * A Utility class for the Inventory list parser
 */
public class InventoryParserUtil {

    private static final String MESSAGE_VALID_QUANTITY = "Please enter a valid quantity, must only contain numbers";

    /**
     * Creates a ShoppingDescriptor constructor with edited values.
     *
     * @param map Map of all the arguments of the sub commands
     * @return ShoppingDescriptor with edited values
     */
    public static InventoryDescriptor createInventoryDescriptor(ArgumentMultimap map) {
        InventoryDescriptor inventoryDescriptor = new InventoryDescriptor();

        if (map.getValue(PREFIX_INVENTORY_NAME).isPresent()) {
            inventoryDescriptor.setName(map.getValue(PREFIX_INVENTORY_NAME).get());
        }
        if (map.getValue(PREFIX_INVENTORY_QUANTITY).isPresent()) {
            checkValidQuantity(map.getValue(PREFIX_INVENTORY_QUANTITY).get());
            inventoryDescriptor.setQuantity(Double.parseDouble(map.getValue(PREFIX_INVENTORY_QUANTITY).get()));
        }
        if (map.getValue(PREFIX_INVENTORY_REMARKS).isPresent()) {
            inventoryDescriptor.setRemarks(map.getValue(PREFIX_INVENTORY_REMARKS).get());
        }

        return inventoryDescriptor;
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
}
