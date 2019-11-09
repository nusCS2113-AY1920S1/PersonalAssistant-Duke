package sgtravel.model.lists;

import sgtravel.commons.exceptions.DukeException;

/**
 * Interface which models a List of planning objects.
 */
interface Listable<T> {

    /**
     * Adds an item to the List.
     *
     * @param item The item to add.
     * @throws DukeException if there is an error in adding.
     */
    void add(T item) throws DukeException;

    /**
     * Gets the item at a given index.
     *
     * @param index The index to search for.
     * @return The item at the given index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    T get(int index) throws IndexOutOfBoundsException;

    /**
     * Returns whether the List is empty.
     *
     * @return true If the List is empty.
     */
    boolean isEmpty();

    /**
     * Returns whether an item is inside the List.
     *
     * @param item The item to check for.
     * @return true If the item exists.
     */
    boolean contains(T item);

    /**
     * Returns the size of the List.
     *
     * @return The size of the List.
     */
    int size();
}
