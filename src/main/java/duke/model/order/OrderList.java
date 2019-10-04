package duke.model.order;

import duke.model.exceptions.OrderNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class OrderList implements Iterable<Order> {

    private final ObservableList<Order> internalList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    public void add(Order toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(target);
        requireNonNull(editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        internalList.set(index, editedOrder);
    }

    public void remove(Order toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new OrderNotFoundException();
        }
    }

    public void setOrders(OrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setOrders(List<Order> orders) {
        //requireAllNonNull(orders);

        internalList.setAll(orders);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Order> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderList // instanceof handles nulls
                && internalList.equals(((OrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
