package duke.model;

import duke.commons.core.index.Index;
import duke.model.inventory.Ingredient;
import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.product.Product;
import duke.model.sale.Sale;
import duke.model.shortcut.Shortcut;
import javafx.collections.ObservableList;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the baking-home level.
 */
public class BakingHome implements ReadOnlyBakingHome {

    private final UniqueEntityList<Sale> sales;
    private final UniqueEntityList<Order> orders;
    private final UniqueEntityList<Product> products;
    private final UniqueEntityList<Item<Ingredient>> inventory;
    private final UniqueEntityList<Item<Ingredient>> shoppingList;
    private final UniqueEntityList<Shortcut> shortcuts;

    /**
     * Creates a BakingHome.
     */
    public BakingHome() {
        sales = new UniqueEntityList<>();
        orders = new UniqueEntityList<>();
        products = new UniqueEntityList<>();
        inventory = new UniqueEntityList<>();
        shoppingList = new UniqueEntityList<>();
        shortcuts = new UniqueEntityList<>();
    }

    public BakingHome(ReadOnlyBakingHome toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /*
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
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
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
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }

    //================Sale operations================

    /**
     * Adds a sale to sales
     * The sale must not already exist in sales.
     */
    public void addSale(Sale s) {
        sales.add(s);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeSale(Sale key) {
        sales.remove(key);
    }

    /**
     * Replaces the contents of the sale list with {@code sales}.
     */
    public void setSales(List<Sale> sales) {
        this.sales.setAll(sales);
    }

    /**
     * Returns true if a sale with the same identity as {@code sale} exists in {@code sales}.
     */
    public boolean hasSale(Sale sale) {
        requireNonNull(sale);
        return sales.contains(sale);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setSale(Sale target, Sale editedSale) {
        requireNonNull(editedSale);

        sales.set(target, editedSale);
    }

    /**
     * Replaces the sale at {@code Index} in the list with {@code editedSale}.
     * {@code Index} must be a valid index
     * {@code target} must exist in sales
     */
    public void setSale(Index index, Sale sale) {
        requireAllNonNull(index, sale);

        sales.set(index, sale);
    }

    @Override
    public ObservableList<Sale> getSaleList() {
        return sales.asUnmodifiableObservableList();
    }


    //============Product operations==============

    /**
     * Adds an product to products.
     * The order must not already exist in orders.
     */
    public void addProduct(Product p) {
        products.add(p);
    }

    public void setProduct(Product originalProduct, Product editedProduct) {
        requireNonNull(editedProduct);

        products.set(originalProduct, editedProduct);
    }

    @Override
    public ObservableList<Product> getProductList() {
        return products.asUnmodifiableObservableList();
    }

    //============Inventory operations==============

    /**
     * Adds an ingredient to the inventory list
     * @param toAdd The ingredient to be added to the inventory list
     */
    public void addInventory(Item<Ingredient> toAdd) {
        inventory.add(toAdd);
    }

    /**
     * Removes an ingredient from the inventory list
     * @param toRemove The ingredient to be removed from the inventory list
     */
    public void removeInventory(Item<Ingredient> toRemove) {
        inventory.remove(toRemove);
    }

    /**
     * Replaces the ingredient at index of the inventory list with the edited ingredient
     *
     * @param toEdit  the ingredient that needs to be edited
     * @param edited the edited ingredient
     */
    public void setInventory(Item<Ingredient> toEdit, Item<Ingredient> edited) {
        requireAllNonNull(toEdit, edited);
        inventory.set(toEdit, edited);
    }

    @Override
    public ObservableList<Item<Ingredient>> getInventoryList() {
        return inventory.asUnmodifiableObservableList();
    }

    //============Shopping operations==============

    @Override
    public ObservableList<Item<Ingredient>> getShoppingList() {
        return shoppingList.asUnmodifiableObservableList();
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
