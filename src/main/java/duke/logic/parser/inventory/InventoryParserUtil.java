package duke.logic.parser.inventory;

import duke.logic.command.inventory.InventoryDescriptor;
import duke.logic.parser.commons.ArgumentMultimap;

import static duke.logic.parser.commons.CliSyntax.*;

public class InventoryParserUtil {
    public static InventoryDescriptor createInventoryDescriptor(ArgumentMultimap map) {
        InventoryDescriptor inventoryDescriptor = new InventoryDescriptor();

        if (map.getValue(PREFIX_INVENTORY_NAME).isPresent()) {
            inventoryDescriptor.setName(map.getValue(PREFIX_INVENTORY_NAME).get());
        }
        if (map.getValue(PREFIX_INVENTORY_QUANTITY).isPresent()) {
            inventoryDescriptor.setQuantity(Integer.parseInt(map.getValue(PREFIX_INVENTORY_QUANTITY).get()));
        }
        if (map.getValue(PREFIX_INVENTORY_REMARKS).isPresent()) {
            inventoryDescriptor.setRemarks(map.getValue(PREFIX_INVENTORY_REMARKS).get());
        }
        return inventoryDescriptor;
    }
}
