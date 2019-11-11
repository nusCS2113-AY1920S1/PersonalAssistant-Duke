package duke.logic.command.inventory;

import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;

/**
 * A Utility class that contains methods used for Inventory classes
 */
public class InventoryCommandUtil {

    /**
     * Edits the ingredient with values inputted by the user in the edit command
     * @param toEdit the current ingredient to be edited
     * @param edited a descriptor with values taken from inputs of the edit command
     * @return a new ingredient constructor with values of edited, and toEdit (if not inputted)
     */
    public static Item<Ingredient> createNewInventory(Item<Ingredient> toEdit, InventoryDescriptor edited) {
        assert toEdit != null;

        String name = edited.getName().orElse(toEdit.getItem().getName());
        Double quantity = edited.getQuantity().orElse(toEdit.getQuantity().getNumber());
        String remarks = edited.getRemarks().orElse(toEdit.getItem().getRemarks());

        return new Item<Ingredient>((new Ingredient(name, remarks)), new Quantity(quantity));
    }
}