package duke.logic.command.product;

import duke.model.Model;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

public class ProductCommandUtil {

    /**
     * Creates a new Product from a productDescriptor.
     * @param toEdit the product to be edited.
     * @param productDescriptor contains the information to edit with.
     * @return edited product.
     */
    public static Product createNewProduct(Product toEdit, ProductDescriptor productDescriptor) {
        assert toEdit != null;

        String newProductName = productDescriptor.getProductName().orElse(toEdit.getProductName());
        Double newRetailPrice = productDescriptor.getRetailPrice().orElse(toEdit.getRetailPrice());
        Double newIngredientCost =
                productDescriptor.getIngredientCost().orElse(toEdit.getIngredientCost());
        Product.Status newStatus = productDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Product(newProductName, newRetailPrice, newIngredientCost, newStatus);
    }

    /**
     * Verifies whether the Ingredient already exists in the InventoryList. If not, add a new Inventory
     * containing the ingredient.
     * @return names of the ingredient added.
     */
    public static String getNewIngredientsName(Model model, Product product) {
        String nameOfIngredientsAdded = "New ingredients created: ";
        IngredientItemList ingredients = product.getIngredients();
        for(Item<Ingredient> ingredient : ingredients) {
            if (!model.hasIngredient(ingredient.getItem())) {
                Item<Ingredient> newIngredient =  new Item<Ingredient>(ingredient.getItem(),
                        Quantity.getDefaultQuantity());
                model.addInventory(newIngredient);
                nameOfIngredientsAdded += ingredient.getItem().getName() + " ";
            };
        }
        return nameOfIngredientsAdded;
    }
}
