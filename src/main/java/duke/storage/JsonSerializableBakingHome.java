package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import duke.logic.command.exceptions.IllegalValueException;
import duke.model.BakingHome;
import duke.model.ReadOnlyBakingHome;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.model.shortcut.Shortcut;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "bakinghome")
class JsonSerializableBakingHome {

    public static final String MESSAGE_DUPLICATE_ENTITY = "Duplicate entities detected.";

    private final List<JsonAdaptedOrder> orders = new ArrayList<>();
    private final List<JsonAdaptedProduct> products = new ArrayList<>();
    private final List<JsonAdaptedIngredientItem> inventory = new ArrayList<>();
    private final List<JsonAdaptedIngredientItem> shoppingList = new ArrayList<>();
    private final List<JsonAdaptedShortcut> shortcuts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBakingHome}.
     */
    @JsonCreator
    public JsonSerializableBakingHome(@JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.orders.addAll(orders);
    }

    /**
     * Converts a given {@code ReadOnlyBakingHome} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBakingHome}.
     */
    public JsonSerializableBakingHome(ReadOnlyBakingHome source) {
        orders.addAll(source.getOrderList().stream().map(JsonAdaptedOrder::new)
                .collect(Collectors.toList()));
        products.addAll(source.getProductList().stream().map(JsonAdaptedProduct::new)
                .collect(Collectors.toList()));
        inventory.addAll(source.getInventoryList().stream().map(JsonAdaptedIngredientItem::new)
                .collect(Collectors.toList()));
        shoppingList.addAll(source.getShoppingList().stream().map(JsonAdaptedIngredientItem::new)
                .collect(Collectors.toList()));
        shortcuts.addAll(source.getShortcutList().stream().map(JsonAdaptedShortcut::new)
                .collect(Collectors.toList()));

    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BakingHome toModelType() throws IllegalValueException {
        BakingHome bakingHome = new BakingHome();
        for (JsonAdaptedOrder jsonAdaptedOrder : orders) {
            Order order = jsonAdaptedOrder.toModelType();
            if (bakingHome.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            }
            bakingHome.addOrder(order);
        }

        for (JsonAdaptedProduct jsonAdaptedProduct : products) {
            Product product = jsonAdaptedProduct.toModelType();
            bakingHome.addProduct(product);
        }

        for (JsonAdaptedIngredientItem jsonAdaptedIngredientItem : inventory) {
            Item<Ingredient> ingredientItem = jsonAdaptedIngredientItem.toModelType();
            bakingHome.addInventory(ingredientItem);
        }

        for (JsonAdaptedIngredientItem jsonAdaptedIngredientItem : shoppingList) {
            Item<Ingredient> ingredientItem = jsonAdaptedIngredientItem.toModelType();
            bakingHome.addShoppingList(ingredientItem);
        }

        for (JsonAdaptedShortcut jsonAdaptedShortcut : shortcuts) {
            bakingHome.setShortcut(jsonAdaptedShortcut.toModelType());
        }

        return bakingHome;
    }

}
