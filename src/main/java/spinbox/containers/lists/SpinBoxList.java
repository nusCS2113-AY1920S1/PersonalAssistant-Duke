package spinbox.containers.lists;

import spinbox.SpinBox;
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
        this.sort();
        this.saveData();
        return element;
    }

    /**
     * Add element into list, without an intermediate save step.
     * @param element to be added from storage.
     * @return added element.
     */
    public T addFromStorage(T element) {
        list.add(element);
        this.sort();
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
        this.sort();
        this.saveData();
        return element;
    }

    /**
     * Updates an item to value.
     * @param index index of element to be set.
     * @param value value to be marked
     * @return element marked done.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     * @throws InvalidIndexException provided Index is out range.
     */
    public T update(int index, boolean value) throws DataReadWriteException, InvalidIndexException {
        try {
            list.get(index).updateDone(value);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
        this.sort();
        this.saveData();
        return list.get(index);
    }

    public int size() {
        return list.size();
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

    /**
     * To view the list for output.
     */
    public abstract List<String> viewList();

    /**
     * Returns list of items containing keyword.
     * @param keyword keyword to be searched.
     * @return a list of string of items.
     */
    public abstract List<String> containsKeyword(String keyword);
}
