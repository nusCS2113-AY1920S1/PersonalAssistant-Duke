package duke.testutil;

import duke.model.product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalProducts {
    public static final Product CHEESE_CAKE =
            new ProductBuilder()
                    .withName("Cheese cake")
                    .withIngredientCost(3.0)
                    .withRetailPrice(5.9)
                    .addIngredientNameAndQuantity("Cream cheese", 3.0)
                    .withStatus(Product.Status.valueOf("ACTIVE"))
                    .build();

    public static final Product EGG_TART =
            new ProductBuilder()
                    .withName("Egg tart")
                    .withIngredientCost(1.0)
                    .withRetailPrice(1.5)
                    .addIngredientNameAndQuantity("Egg", 3.0)
                    .addIngredientNameAndQuantity("Flour", 1.0)
                    .withStatus(Product.Status.valueOf("ACTIVE"))
                    .build();

    private TypicalProducts() {}

    public static List<Product> getTypicalProducts() {
        return new ArrayList<>(Arrays.asList(CHEESE_CAKE, EGG_TART));
    }
}
