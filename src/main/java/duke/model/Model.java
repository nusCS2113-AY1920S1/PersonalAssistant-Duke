package duke.model;

import duke.commons.core.index.Index;
import duke.model.order.Order;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Predicate;

/**
 * The API of the Model component.
 */
public interface Model {
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /**
     * Returns the BakingHome.
     */
    ReadOnlyBakingHome getBakingHome();

    /**
     * Replaces baking home data with the data in {@code bakingHome}.
     */
    void setBakingHome(ReadOnlyBakingHome bakingHome);

    //========Order operations========

    /**
     * Returns true if an order with the same id as {@code order} exists in order list.
     */
    boolean hasOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in order list.
     */
    void deleteOrder(Order target);

    /**
     * Adds the given order.
     * The order must not exist in order list
     */
    void addOrder(Order order);

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in order list
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Replaces the order at {@code Index} in the list with {@code editedOrder}.
     * {@code Index} must be a valid index
     * {@code target} must exist in order list
     */
    void setOrder(Index index, Order order);

    /**
     * Returns an unmodifiable view of the filtered order list.
     */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    //========Product operations=========

    //========Finance operations=========

    //========Ingredient operations======

    //======Shopping list operations=====

    //=========Shortcut operations=======

    /**
     * Adds {@code shortcut} to shortcut list.
     * If the shortcut already exists, it overrides the old shortcut.
     */
    void setShortcut(Shortcut shortcut);

    /**
     * Deletes the given {@code shortcut}.
     * The shortcut must exist in order list.
     */
    void removeShortcut(Shortcut shortcut);

    /**
     * Returns true if a shortcut with the same name as {@code order} exists in shortcut list.
     */
    boolean hasShortcut(Shortcut shortcut);

    /**
     * Returns an unmodifiable view of the shortcut list.
     */
    List<Shortcut> getShortcutList();
}
