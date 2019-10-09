package duke.model;

import duke.model.order.Order;
import duke.model.order.OrderList;
import duke.model.sale.Sale;
import duke.model.sale.SaleList;
import javafx.collections.ObservableList;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class BakingHome implements ReadOnlyBakingHome {

    private final OrderList orders;
    private final SaleList sales;

    public BakingHome() {
        orders = new OrderList();
        sales = new SaleList();
    }

    public BakingHome(ReadOnlyBakingHome toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the order list with {@code orders}.
     */
    public void setOrders(List<Order> orders) {
        this.orders.setOrders(orders);
    }

    /**
     * Replaces the contents of the sale list with {@code orders}.
     */
    public void setSales(List<Sale> sales) {
        this.sales.setSales(sales);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBakingHome newData) {
        requireNonNull(newData);

        setOrders(newData.getOrderList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orders.contains(order);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addOrder(Order o) {
        orders.add(o);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addSale(Sale s) {
        sales.add(s);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setSale(Sale target, Sale editedSale) {
        requireNonNull(editedSale);

        sales.setSale(target, editedSale);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return orders.asUnmodifiableObservableList().size() + " orders";
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }

    // @Override
    public ObservableList<Sale> getSaleList() {
        return sales.asUnmodifiableObservableList();
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
