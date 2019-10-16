package duke.logic.command.inventory;

import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;

public class InventoryCommandUtil {
    public static Item<Ingredient> createNewInventory(Item<Ingredient> toEdit, InventoryDescriptor edited) {
        assert toEdit != null;

        String name = edited.getName().orElse(toEdit.getItem().getName());
        Double quantity = edited.getQuantity().orElse(toEdit.getQuantity().getNumber());
        String remarks = edited.getRemarks().orElse(toEdit.getItem().getRemarks());


        return new Item<Ingredient>((new Ingredient(name, remarks)), new Quantity(quantity));
    }
}