package duke.model;

import duke.commons.core.index.Index;
import duke.model.exceptions.OrderNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class BakingHomeList<T> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    public void add(T toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public void set(T target, T edited) {
        requireNonNull(target);
        requireNonNull(edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        internalList.set(index, edited);
    }

    public void set(Index index, T edited) {
        requireNonNull(index);
        requireNonNull(edited);

        if (index.getZeroBased() < 0 || index.getZeroBased() >= internalList.size()) {
            throw new OrderNotFoundException();
        }

        internalList.set(index.getZeroBased(), edited);
    }


    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new OrderNotFoundException();
        }
    }

    public void setAll(BakingHomeList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setAll(List<T> replacement) {
        requireNonNull(replacement);

        internalList.setAll(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BakingHomeList // instanceof handles nulls
                && internalList.equals(((BakingHomeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
