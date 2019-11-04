package duke.testutil;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

/**
 * A utility class to help with building Product objects.
 */
public class ProductBuilder {
    //public static final String DEFAULT_NAME = "Cheese cake";
    public static final Double DEFAULT_COST = 0.0;
    public static final Double DEFAULT_PRICE = 0.0;
    public static final Product.Status DEFAULT_STATUS = Product.Status.valueOf("ACTIVE");

    public static final Item<Ingredient> DEFAULT_INGREDIENT_ITEM = IngredientBuilder.buildIngredientItemWithQuantity(
            "Cream Cheese", 0.0);
    public static final IngredientItemList DEFAULT_INGREDIENTS =
            new IngredientItemList();

    private String productName;
    private IngredientItemList ingredients = new IngredientItemList();;
    private Double ingredientCost;
    private Double retailPrice;
    private Product.Status status;

    public ProductBuilder() {
    }

    public ProductBuilder(String productName) {
        this.productName = productName;
        ingredients = new IngredientItemList();
        ingredientCost = DEFAULT_COST;
        retailPrice = DEFAULT_PRICE;
        status = DEFAULT_STATUS;
    }

    public ProductBuilder(Product productToCopy) {
        productName = productToCopy.getProductName();
        ingredients = productToCopy.getIngredients();
        retailPrice = productToCopy.getRetailPrice();
        ingredientCost = productToCopy.getIngredientCost();
        status = productToCopy.getStatus();
    }

    public Product build() {
        return new Product(productName, retailPrice, ingredientCost, ingredients, status);
    }

    public ProductBuilder withName(String name) {
        this.productName = name;
        return this;
    }

    public ProductBuilder withRetailPrice(Double price) {
        this.retailPrice = price;
        return this;
    }

    public ProductBuilder withIngredientCost(Double cost) {
        this.ingredientCost = cost;
        return this;
    }

    public ProductBuilder addIngredientName(String name) {
        this.ingredients.add(IngredientBuilder.buildIngredientItem(name));
        return this;
    }

    public ProductBuilder addIngredientNameAndQuantity(String name, Double quantity) {
        this.ingredients.add(IngredientBuilder.buildIngredientItemWithQuantity(name, quantity));
        return this;
    }

    public ProductBuilder withStatus(Product.Status status) {
        this.status = status;
        return this;
    }

}
