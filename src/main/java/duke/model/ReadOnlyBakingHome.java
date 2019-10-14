package duke.model;

import duke.model.commons.Ingredient;
import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Unmodifiable view of an address book.
 */
public interface ReadOnlyBakingHome {

    /**
     * Returns an unmodifiable view of the order list.
     */
    ObservableList<Order> getOrderList();

    ObservableList<Product> getProductList();

    ObservableList<Item<Ingredient>> getInventoryList();

    List<Shortcut> getShortcutList();
}
