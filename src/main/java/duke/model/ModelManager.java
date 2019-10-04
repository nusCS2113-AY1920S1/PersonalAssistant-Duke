package duke.model;

import duke.model.order.Order;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class ModelManager implements Model {
    private final BakingHome bakingHome;
    private final FilteredList<Order> filteredOrders;

    public ModelManager(ReadOnlyBakingHome bakingHome) {
        super();
        this.bakingHome = new BakingHome(bakingHome);
        this.filteredOrders = new FilteredList<>(this.bakingHome.getOrderList());
    }

    public ModelManager() {
        this(new BakingHome());
    }

    @Override
    public void setBakingHome(ReadOnlyBakingHome bakingHome) {
        this.bakingHome.resetData(bakingHome);
    }

    @Override
    public ReadOnlyBakingHome getBakingHome() {
        return this.bakingHome;
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return bakingHome.getOrderList().contains(order);
    }

    @Override
    public void deleteOrder(Order target) {
        bakingHome.getOrderList().remove(target);
    }

    @Override
    public void addOrder(Order order) {
        bakingHome.addOrder(order);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(target);
        requireNonNull(editedOrder);

        bakingHome.setOrder(target, editedOrder);
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }
}
