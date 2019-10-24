package duke.model;

import duke.commons.core.index.Index;
import duke.model.exceptions.DuplicateEntityException;
import duke.model.exceptions.EntityNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A list of elements that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class UniqueEntityList<T> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an element to the list.
     *
     * @throws DuplicateEntityException if the element to add is a duplicate of an existing card in the list.
     */
    public void add(T toAdd) throws DuplicateEntityException {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateEntityException();
        }

        internalList.add(toAdd);
    }

    /**
     * Replaces {@code target} in the list with {@code edited}.
     *
     * @throws DuplicateEntityException if the replacement is equivalent to another existing element in the list.
     * @throws EntityNotFoundException  if {@code target} could not be found in the list.
     */
    public void set(T target, T edited) throws DuplicateEntityException, EntityNotFoundException {
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

    /**
     * Replaces {@code target} in the list with {@code edited}.
     *
     * @throws IndexOutOfBoundsException if {@code index} is out of range of the list
     */
    public void set(Index index, T edited) throws IndexOutOfBoundsException {
        requireNonNull(index);
        requireNonNull(edited);

        if (index.getZeroBased() < 0 || index.getZeroBased() >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }

        internalList.set(index.getZeroBased(), edited);
    }

    /**
     * Removes the equivalent element from the list.
     *
     * @throws EntityNotFoundException if no such element could be found in the list.
     */
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

    /**
     * Replaces the list with {@code replacement}.
     */
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
}
