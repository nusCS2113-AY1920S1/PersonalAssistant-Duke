package duke.model;

import duke.model.order.Order;
import duke.model.sale.Sale;
import javafx.collections.ObservableList;

import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;
    Predicate<Sale> PREDICATE_SHOW_ALL_SALES = unused -> true;

    void setBakingHome(ReadOnlyBakingHome bakingHome);

    ReadOnlyBakingHome getBakingHome();

    boolean hasOrder(Order order);

    void deleteOrder(Order target);

    void addOrder(Order order);

    void setOrder(Order target, Order editedOrder);

    ObservableList<Order> getFilteredOrderList();

    boolean hasSale(Sale sale);

    void deleteSale(Sale target);

    void addSale(Sale sale);

    void setSale(Sale target, Sale editedSale);

    void updateFilteredPersonList(Predicate<Order> predicate);


}
