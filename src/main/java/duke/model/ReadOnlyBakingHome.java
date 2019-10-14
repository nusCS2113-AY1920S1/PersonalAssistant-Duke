package duke.model;

import duke.model.commons.Ingredient;
import duke.model.order.Order;
import duke.model.sale.Sale;
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

    /**
     * Returns an unmodifiable view of the sale list.
     */
    ObservableList<Sale> getSaleList();

    /**
     * Returns an unmodifiable view of the product list.
     */
    ObservableList<Product> getProductList();

    /**
     * Returns an unmodifiable view of the inventory list.
     */
    ObservableList<Ingredient> getInventoryList();

    List<Shortcut> getShortcutList();
}