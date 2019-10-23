package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class JsonAdaptedProduct {
    private final String productName;
    private List<JsonAdaptedIngredientItem> ingredientItems = new ArrayList<>();
    private final Double ingredientCost;
    private final Double retailPrice;
    private final Product.Status status;

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedProduct(
            @JsonProperty("productName") String productName,
            @JsonProperty("ingredientItems") List<JsonAdaptedIngredientItem> ingredientItems,
            @JsonProperty("ingredientCost") Double ingredientCost,
            @JsonProperty("retailPrice") Double retailPrice,
            @JsonProperty("status") Product.Status status) {
        this.productName = productName;
        this.ingredientItems = ingredientItems;
        this.ingredientCost = ingredientCost;
        this.retailPrice = retailPrice;
        this.status = status;
    }

    /**
     * Creates a jackson-friendly product from {@code source}.
     */
    public JsonAdaptedProduct(Product source) {
        this.productName = source.getProductName();
        this.ingredientCost = source.getIngredientCost();
        this.retailPrice = source.getRetailPrice();
        this.status = source.getStatus();
        for (Item<Ingredient> ingredientItem : source.getIngredients()) {
            this.ingredientItems.add(
                    new JsonAdaptedIngredientItem(
                            new JsonAdaptedIngredient(ingredientItem.getItem()),
                            ingredientItem.getQuantity().getNumber()
                    )
            );
        }
    }

    /**
     * Converts a given {@code Product} into this class for Jackson use.
     */
    public Product toModelType() {
        Product product = new Product();
        product.setProductName(productName);
        product.setIngredientCost(ingredientCost);
        product.setRetailPrice(retailPrice);
        product.setStatus(this.status);

        IngredientItemList ingredientItemList = new IngredientItemList();
        for (JsonAdaptedIngredientItem jsonAdaptedIngredientItem : ingredientItems) {
            ingredientItemList.add(jsonAdaptedIngredientItem.toModelType());

        }
        product.setIngredients(ingredientItemList);

        return product;
    }
}
