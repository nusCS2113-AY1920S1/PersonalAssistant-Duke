package duke.logic.command.product;

import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.StringUtils;

import static java.util.Objects.requireNonNull;

public class ProductCommandUtil {
    public static Double NOT_SPECIFIED_COST = -1.0;

    /**
     * Creates a new Product using information from a productDescriptor and previous product.
     * @param toEdit the product to be edited.
     * @param productDescriptor contains the information to edit with.
     * @return edited product.
     */
    public static Product getEditedProductFromDescriptor(Product toEdit, ProductDescriptor productDescriptor) {
        assert toEdit != null;

        String newProductName =
            StringUtils.capitalize((productDescriptor.getProductName().orElse(toEdit.getProductName())).toLowerCase());

        Double newRetailPrice = productDescriptor.getRetailPrice().orElse(toEdit.getRetailPrice());
        Double newIngredientCost =
                productDescriptor.getIngredientCost().orElse(toEdit.getIngredientCost());
        IngredientItemList ingredientItemList =
                productDescriptor.getIngredientItemList().orElse(toEdit.getIngredients());
        Product.Status newStatus = productDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Product(newProductName, newRetailPrice, newIngredientCost, ingredientItemList, newStatus);
    }

    /**
     * Creates a new Product using information from a productDescriptor.
     * @param productDescriptor contains the information to edit with
     * @return edited product
     */
    public static Product getAddedProductFromDescriptor(ProductDescriptor productDescriptor) throws ParseException {

        if (!productDescriptor.getProductName().isPresent()) {
            throw new ParseException(ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);
        }
        String s = productDescriptor.getProductName().get();
        String name = StringUtils.capitalize(s.toLowerCase());
        if (name.isBlank() || name.isEmpty()) {
            throw new ParseException(ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME);
        }
        Product product = new Product(name);
        if (!productDescriptor.getIngredientCost().isEmpty()) {
            product.setIngredientCost(productDescriptor.getIngredientCost().get());
        } else {
            product.setIngredientCost(NOT_SPECIFIED_COST);
        }
        product.setRetailPrice(productDescriptor.getRetailPrice().orElse(Product.DEFAULT_RETAIL_PRICE));
        product.setIngredients(productDescriptor.getIngredientItemList().orElse(new IngredientItemList()));
        return product;
    }

    /**
     * Verifies whether the Ingredient already exists in the InventoryList. If not, adds a new Inventory
     * containing the ingredient to the model.
     */
    public static void verifyNewIngredients(Model model, Product product) {
        IngredientItemList ingredients = product.getIngredients();
        for (Item<Ingredient> ingredient : ingredients) {
            if (!model.hasShoppingList(ingredient)) {
                Item<Ingredient> newIngredient =  new Item<Ingredient>(ingredient.getItem(),
                        Quantity.getDefaultQuantity());
                model.addShoppingList(newIngredient);
            }
        }
    }

    /**
     * Calculates the default Ingredient cost of a product using ingredients in the product.
     */
    public static Double getIngredientCost(Model model, Product product) {
        requireNonNull(product);
        IngredientItemList ingredients = product.getIngredients();
        if (ingredients.isEmpty()) {
            return Product.DEFAULT_INGREDIENT_COST;
        }
        return model.computeTotalCost(ingredients);
    }
}
