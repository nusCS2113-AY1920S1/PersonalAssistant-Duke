package duke.model;

import duke.commons.core.index.Index;
import duke.model.order.Order;
import javafx.collections.ObservableList;

import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    void setBakingHome(ReadOnlyBakingHome bakingHome);

    ReadOnlyBakingHome getBakingHome();

    boolean hasOrder(Order order);

    void deleteOrder(Order target);

    void addOrder(Order order);

    void setOrder(Order target, Order editedOrder);

    void setOrder(Index index, Order order);

    ObservableList<Order> getFilteredOrderList();

    void updateFilteredPersonList(Predicate<Order> predicate);
}
