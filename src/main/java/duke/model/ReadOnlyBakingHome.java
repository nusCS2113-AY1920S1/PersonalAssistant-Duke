package duke.model;

import duke.model.order.Order;
import duke.model.sale.Sale;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book.
 */
public interface ReadOnlyBakingHome {

    /**
     * Returns an unmodifiable view of the order list.
     */
    ObservableList<Order> getOrderList();

}