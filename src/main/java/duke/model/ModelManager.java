package duke.model;

import duke.commons.core.index.Index;
import duke.model.order.Order;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;
import java.util.function.Predicate;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * Represents the in-memory model of baking home data.
 */
public class ModelManager implements Model {
    private final BakingHome bakingHome;
    private final FilteredList<Order> filteredOrders;

    /**
     * Initializes a ModelManager with the given BakingHome.
     */
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

    //================Order operations=================

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
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(target);
        requireNonNull(editedOrder);

        bakingHome.setOrder(target, editedOrder);
    }

    @Override
    public void setOrder(Index index, Order order) {
        requireAllNonNull(index, order);

        bakingHome.setOrder(index, order);
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //========Shortcut operations=========

    @Override
    public void setShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);

        bakingHome.setShortcut(shortcut);
    }

    @Override
    public void removeShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);
        bakingHome.removeShortcut(shortcut);
    }

    @Override
    public boolean hasShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);
        return bakingHome.hasShortcut(shortcut);
    }

    @Override
    public List<Shortcut> getShortcutList() {
        return bakingHome.getShortcutList();
    }

}
