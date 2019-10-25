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
        IngredientItemList ingredientItemList =
                productDescriptor.getIngredientItemList().orElse(toEdit.getIngredients());
        Product.Status newStatus = productDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Product(newProductName, newRetailPrice, newIngredientCost, ingredientItemList, newStatus);
    }

    /**
     * Verifies whether the Ingredient already exists in the InventoryList. If not, adds a new Inventory
     * containing the ingredient to the model.
     * @return true if new Ingredient is added to the inventory
     */
    public static void verifyNewIngredient(Model model, Product product) {
        IngredientItemList ingredients = product.getIngredients();
        for(Item<Ingredient> ingredient : ingredients) {
            if (!model.hasIngredient(ingredient.getItem())) {
                Item<Ingredient> newIngredient =  new Item<Ingredient>(ingredient.getItem(),
                        Quantity.getDefaultQuantity());
                model.addInventory(newIngredient);
            }
        }
    }
}
