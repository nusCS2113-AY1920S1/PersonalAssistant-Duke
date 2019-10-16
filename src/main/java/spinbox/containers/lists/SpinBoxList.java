package spinbox.containers.lists;

import spinbox.Storage;
import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.InvalidIndexException;
import spinbox.entities.items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class SpinBoxList<T extends Item> {
    static final String DIRECTORY_NAME = "SpinBoxData/";
    protected List<T> list;
    private String parentCode;
    Storage localStorage;

    /**
     * Constructor.
     */
    SpinBoxList(String parentCode) {
        this.list = new ArrayList<T>();
        this.parentCode = parentCode;
    }

    /**
     * Return SpinBoxList as list.
     * @return list.
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Gets the module code of the module containing an instance of this list.
     * @return the module code as a String.
     */
    String getParentCode() {
        return parentCode;
    }

    /**
     * Add element into list.
     * @param element to be added.
     * @return added element.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    public T add(T element) throws DataReadWriteException {
        list.add(element);
        this.saveData();
        return element;
    }

    /**
     * Remove element at index from list.
     * @param index index of element.
     * @return element removed.
     * @throws InvalidIndexException provided Index is out range.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    public T remove(int index) throws DataReadWriteException, InvalidIndexException {
        try {
            T removedItem = list.remove(index);
            this.saveData();
            return removedItem;
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Return element at index.
     * @param index index of element.
     * @return element at index.
     * @throws InvalidIndexException provided Index is out range.
     */
    public T get(int index) throws InvalidIndexException {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Replace element at index with element.
     * @param index index of element to be replaced.
     * @param element new element to be inserted.
     * @return new element.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     * @throws InvalidIndexException provided Index is out range.
     */
    public T replace(int index, T element) throws DataReadWriteException, InvalidIndexException {
        try {
            list.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
        this.saveData();
        return element;
    }

    /**
     * Marks an item as done.
     * @param index index of element to be marked done.
     * @return element marked done.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     * @throws InvalidIndexException provided Index is out range.
     */
    public T mark(int index) throws DataReadWriteException, InvalidIndexException {
        try {
            list.get(index).markDone();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
        this.saveData();
        return list.get(index);
    }

    /**
     * Sort the list.
     */
    public abstract void sort();

    /**
     * To populate data into this list from the list's localStorage.
     */
    public abstract void loadData() throws DataReadWriteException, CorruptedDataException;

    /**
     * To save current list data into the list's localStorage.
     */
    public abstract void saveData() throws DataReadWriteException;
}
