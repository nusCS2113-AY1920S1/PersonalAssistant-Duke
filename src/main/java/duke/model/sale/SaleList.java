package duke.model.sale;

import duke.model.exceptions.SaleNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class SaleList implements Iterable<Sale> {
    private final ObservableList<Sale> internalList = FXCollections.observableArrayList();
    private final ObservableList<Sale> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public SaleList() {

    }

    public boolean contains(Sale toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    public void add(Sale toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void setSale(Sale target, Sale editedSale) {
        requireNonNull(target);
        requireNonNull(editedSale);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SaleNotFoundException();
        }

        internalList.set(index, editedSale);
    }

    public void remove(Sale toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SaleNotFoundException();
        }
    }

    public void setSale(SaleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setSales(List<Sale> sales) {
        //requireAllNonNull(orders);

        internalList.setAll(sales);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Sale> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Sale> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleList // instanceof handles nulls
                && internalList.equals(((SaleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}