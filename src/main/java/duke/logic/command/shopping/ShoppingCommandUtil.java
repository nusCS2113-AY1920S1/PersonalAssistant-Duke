package duke.logic.command.shopping;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Quantity;

public class ShoppingCommandUtil {
    public static Item<Ingredient> createNewIngredient(Item<Ingredient> toEdit, ShoppingDescriptor edited) {
        assert toEdit != null;

        String name = edited.getName().orElse(toEdit.getItem().getName());
        Integer quantity = edited.getQuantity().orElse(toEdit.getQuantity().getNumber());
        String remarks = edited.getRemarks().orElse(toEdit.getItem().getRemarks());
        Double unitCost = edited.getUnitCost().orElse(toEdit.getItem().getUnitPrice());

        return new Item<Ingredient>((new Ingredient(name, unitCost, remarks)), new Quantity(quantity));
    }
}
