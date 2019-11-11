package duke.logic.command.shopping;

import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;

/**
 * A Utility class that contains methods used for Shopping List classes
 */
public class ShoppingCommandUtil {
    /**
     * Edits the ingredient with values inputted by the user in the edit command
     * @param toEdit the current ingredient to be edited
     * @param edited a descriptor with values taken from inputs of the edit command
     * @return a new ingredient constructor with values of edited, and toEdit (if not inputted)
     */
    public static Item<Ingredient> createNewIngredient(Item<Ingredient> toEdit, ShoppingDescriptor edited) {
        assert toEdit != null;

        String name = edited.getName().orElse(toEdit.getItem().getName());
        Double quantity = edited.getQuantity().orElse(toEdit.getQuantity().getNumber());
        String remarks = edited.getRemarks().orElse(toEdit.getItem().getRemarks());
        Double unitCost = edited.getUnitCost().orElse(toEdit.getItem().getUnitPrice());

        return new Item<Ingredient>((new Ingredient(name, unitCost, remarks)), new Quantity(quantity));
    }

    /**
     * Edits the ingredient with a new quantity
     * @param toEdit the current ingredient to be edited
     * @param newQuantity the new quantity of the ingredient
     * @return a new ingredient constructor with its quantity edited to the new quantity.
     */
    public static Item<Ingredient> createNewIngredient(Item<Ingredient> toEdit, Double newQuantity) {
        assert toEdit != null;

        String name = toEdit.getItem().getName();
        Double quantity = newQuantity;
        String remarks = toEdit.getItem().getRemarks();
        Double unitCost = toEdit.getItem().getUnitPrice();

        return new Item<Ingredient>((new Ingredient(name, unitCost, remarks)), new Quantity(quantity));
    }
}
