package spinbox.lists;

import spinbox.Storage;
import spinbox.exceptions.StorageException;
import spinbox.items.Item;

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
     * Constructor if already have list.
     * @param list list to be made into SpinBoxList.
     */
    SpinBoxList(List<T> list, String parentCode) {
        this.list = list;
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
     * @throws StorageException saveData fail due to I/O Error.
     */
    public T add(T element) throws StorageException {
        list.add(element);
        this.saveData();
        return element;
    }

    /**
     * Remove element at index from list.
     * @param index index of element.
     * @return element removed.
     * @throws IndexOutOfBoundsException if index is invalid.
     * @throws StorageException saveData fail due to I/O Error.
     */
    public T remove(int index) throws IndexOutOfBoundsException, StorageException {
        this.saveData();
        return list.remove(index);
    }

    /**
     * Return element at index.
     * @param index index of element.
     * @return element at index.
     */
    public T get(int index) {
        return list.get(index);
    }

    /**
     * Replace element at index with element.
     * @param index index of element to be replaced.
     * @param element new element to be inserted.
     * @return new element.
     * @throws StorageException saveData fail due to I/O Error.
     */
    public T replace(int index, T element) throws StorageException {
        list.set(index, element);
        this.saveData();
        return element;
    }

    /**
     * Marks an item as done.
     * @param index index of element to be marked done.
     * @return element marked done.
     * @throws StorageException saveData fail due to I/O Error.
     */
    public T mark(int index) throws StorageException {
        list.get(index).markDone();
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
    public abstract void loadData() throws StorageException;

    /**
     * To save current list data into the list's localStorage.
     */
    public abstract void saveData() throws StorageException;
}
