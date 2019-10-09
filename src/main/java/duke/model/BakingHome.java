package duke.model;

import duke.commons.core.index.Index;
import duke.model.product.Product;
import duke.model.order.Order;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the baking-home level.
 */
public class BakingHome implements ReadOnlyBakingHome {

    private final UniqueEntityList<Order> orders;
    private final UniqueEntityList<Product> products;
    private final UniqueEntityList<Shortcut> shortcuts;

    /**
     * Creates a BakingHome.
     */
    public BakingHome() {
        orders = new UniqueEntityList<>();
        products = new UniqueEntityList<>();
        shortcuts = new UniqueEntityList<>();
    }

    public BakingHome(ReadOnlyBakingHome toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code BakingHome} with {@code newData}.
     */
    public void resetData(ReadOnlyBakingHome newData) {
        requireNonNull(newData);

        setOrders(newData.getOrderList());
        setShortcuts(newData.getShortcutList());

    }

    //================Order operations================

    /**
     * Replaces the contents of the order list with {@code orders}.
     */
    public void setOrders(List<Order> orders) {
        this.orders.setAll(orders);
    }

    /**
     * Returns true if an order with the same identity as {@code order} exists in {@code orders}.
     */
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orders.contains(order);
    }

    /**
     * Adds an order to orders
     * The order must not already exist in orders.
     */
    public void addOrder(Order o) {
        orders.add(o);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in orders
     */
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(editedOrder);

        orders.set(target, editedOrder);
    }

    /**
     * Replaces the order at {@code Index} in the list with {@code editedOrder}.
     * {@code Index} must be a valid index
     * {@code target} must exist in orders
     */
    public void setOrder(Index index, Order order) {
        requireAllNonNull(index, order);

        orders.set(index, order);
    }

    /**
     * Removes {@code key} from {@code orders}.
     * {@code key} must exist in {@code orders}.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }

    //============Product operations==============

    /**
     * Adds an product to products.
     * The order must not already exist in orders.
     */
    public void addProduct(Product p) {
        products.add(p);
    }

    public void setProduct(Product originalProduct, Product editedOrder) {
        requireNonNull(editedOrder);

        products.set(originalProduct, editedOrder);
    }
    @Override
    public ObservableList<Product> getProductList() {
        return products.asUnmodifiableObservableList();
    }


    //// shortcut-related operations

    /**
     * Adds {@code shortcut} to shortcut list.
     * If the shortcut already exists, it overrides the old shortcut.
     */
    public void setShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);

        if (shortcuts.contains(shortcut)) {
            shortcuts.set(shortcut, shortcut);
        } else {
            shortcuts.add(shortcut);
        }
    }

    /**
     * Replaces the contents of the shortcut list with {@code shortcuts}.
     */
    public void setShortcuts(List<Shortcut> shortcuts) {
        this.shortcuts.setAll(shortcuts);
    }

    /**
     * Deletes the given {@code shortcut}.
     * The shortcut must exist in order list.
     */
    public void removeShortcut(Shortcut key) {
        requireNonNull(key);
        shortcuts.remove(key);
    }

    /**
     * Returns true if a shortcut with the same name as {@code order} exists in shortcut list.
     */
    public boolean hasShortcut(Shortcut shortcut) {
        requireNonNull(shortcut);
        return shortcuts.contains(shortcut);
    }

    @Override
    public List<Shortcut> getShortcutList() {
        return shortcuts.asUnmodifiableObservableList();
    }


    //// util methods

    @Override
    public String toString() {
        return orders.asUnmodifiableObservableList().size() + " orders";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BakingHome // instanceof handles nulls
                && orders.equals(((BakingHome) other).orders));
    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }
}
