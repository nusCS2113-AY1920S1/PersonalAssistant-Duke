package duke.model;

import duke.commons.core.index.Index;
import duke.model.exceptions.DuplicateEntityException;
import duke.model.exceptions.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class UniqueEntityList<T> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    public void add(T toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateEntityException();
        }

        internalList.add(toAdd);
    }

    public void set(T target, T edited) {
        requireNonNull(target);
        requireNonNull(edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntityNotFoundException();
        }

        if (!target.equals(edited) && contains(edited)) {
            throw new DuplicateEntityException();
        }

        internalList.set(index, edited);
    }

    public void set(Index index, T edited) {
        requireNonNull(index);
        requireNonNull(edited);

        if (index.getZeroBased() < 0 || index.getZeroBased() >= internalList.size()) {
            throw new EntityNotFoundException();
        }

        internalList.set(index.getZeroBased(), edited);
    }


    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntityNotFoundException();
        }
    }

    public void setAll(UniqueEntityList<T> replacement) {
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
                || (other instanceof UniqueEntityList // instanceof handles nulls
                && internalList.equals(((UniqueEntityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code items} contains only unique items.
     */
    private boolean itemsAreUnique(List<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).equals(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
