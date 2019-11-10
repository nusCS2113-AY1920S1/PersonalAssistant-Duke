package spinbox.containers.lists;

import spinbox.exceptions.DateFormatException;
import spinbox.datapersistors.storage.Storage;

import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.InvalidIndexException;
import spinbox.entities.items.Item;
import spinbox.datapersistors.storage.StorageContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SpinBoxList<T extends Item> implements StorageContainer {
    private static final Logger LOGGER = Logger.getLogger(SpinBoxList.class.getName());
    private static final String LOG_GET_LIST = "Returned copy of list.";
    private static final String LOG_ADD_ELEMENT = "Added element into list.";
    private static final String LOG_ADD_FROM_STORAGE = "Added element from storage and sorted list.";
    private static final String LOG_REMOVE_ELEMENT = "Removed element and stored list.";
    private static final String LOG_INVALID_INDEX = "Index inputted is invalid.";
    private static final String LOG_UPDATE = "Updated element in list.";
    private static final String LOG_OUTPUT_NUMBERED_LIST = "Returned numbered list with header.";
    static final String DIRECTORY_NAME = "SpinBoxData/";
    protected List<T> list;
    private String parentCode;
    Storage localStorage;

    /**
     * Constructor for list that creates a private list and stores the parent code.
     */
    SpinBoxList(String parentCode) {
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "Constructor");
        this.list = new ArrayList<>();
        this.parentCode = parentCode;
        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    /**
     * Returns a copy of the list.
     * @return Returns copy of the list.
     */
    public List<T> getList() {
        LOGGER.entering(getClass().getName(), "getList");
        List<T> listCopy = new ArrayList<>(list);
        LOGGER.fine(LOG_GET_LIST);
        LOGGER.exiting(getClass().getName(), "getList");
        return listCopy;
    }

    /**
     * Returns the size of the list.
     * @return Returns the size of the list.
     */
    public int size() {
        return list.size();
    }

    /**
     * Gets the module code of the module containing an instance of this list.
     * @return Returns the module code as a String.
     */
    String getParentCode() {
        return parentCode;
    }

    /**
     * Adds element into list.
     * @param element The element to be added.
     * @return The element that was added.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    public T add(T element) throws DataReadWriteException {
        LOGGER.entering(getClass().getName(), "add");
        list.add(element);
        this.sortAndSaveData();
        LOGGER.fine(LOG_ADD_ELEMENT);
        LOGGER.exiting(getClass().getName(), "add");
        return element;
    }

    /**
     * Add element into list, without an intermediate save step.
     * @param element The element to be added from storage.
     * @return The element that was added.
     */
    public T addFromStorage(T element) {
        LOGGER.entering(getClass().getName(), "addFromStorage");
        list.add(element);
        this.sort();
        LOGGER.fine(LOG_ADD_FROM_STORAGE);
        LOGGER.exiting(getClass().getName(), "addFromStorage");
        return element;
    }

    /**
     * Removes element at index from list.
     * @param index The index of element to be removed.
     * @return The element that was removed.
     * @throws InvalidIndexException Provided Index is out range.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    public T remove(int index) throws DataReadWriteException, InvalidIndexException {
        LOGGER.entering(getClass().getName(), "remove");
        try {
            this.saveData();
            T removedItem = list.remove(index);
            LOGGER.fine(LOG_REMOVE_ELEMENT);
            LOGGER.exiting(getClass().getName(), "remove");
            return removedItem;
        } catch (IndexOutOfBoundsException e) {
            LOGGER.warning(LOG_INVALID_INDEX);
            throw new InvalidIndexException();
        }
    }

    /**
     * Returns element at index.
     * @param index The index of element to be returned.
     * @return The element at the index.
     * @throws InvalidIndexException provided Index is out range.
     */
    public T get(int index) throws InvalidIndexException {
        LOGGER.entering(getClass().getName(), "get");
        try {
            LOGGER.exiting(getClass().getName(), "get");
            return list.get(index);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.warning(LOG_INVALID_INDEX);
            throw new InvalidIndexException();
        }
    }

    /**
     * Updates an item to value.
     * @param index The index of element to be updated.
     * @param value The value the element is to be updated to.
     * @return The updated element with updated value.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     * @throws InvalidIndexException Provided Index is out range.
     */
    public T update(int index, boolean value) throws DataReadWriteException, InvalidIndexException {
        LOGGER.entering(getClass().getName(), "update");
        try {
            this.get(index).updateDone(value);
            T updatedItem = this.get(index);
            assert updatedItem.getDone() == value : "Updated value is incorrect";
            this.sortAndSaveData();
            LOGGER.fine(LOG_UPDATE);
            LOGGER.exiting(getClass().getName(), "update");
            return updatedItem;
        } catch (IndexOutOfBoundsException e) {
            LOGGER.warning(LOG_INVALID_INDEX);
            throw new InvalidIndexException();
        }
    }

    /**
     * Sorts the list and saves it, used when the list has been modified.
     * @throws DataReadWriteException saveData fail due to I/O Error.
     */
    public void sortAndSaveData() throws DataReadWriteException {
        this.sort();
        this.saveData();
    }

    /**
     * Makes a list with the header as the first element, and the inputList elements numbered in string format.
     * @param header A string that describes the inputList and will be shown in the output.
     * @param inputList A list of elements that are to be converted to strings and numbered.
     * @return A list with the header as first element, and the elements of input list numbered and in string format.
     */
    public List<String> outputNumberedListWithHeader(String header, List<T> inputList) {
        LOGGER.entering(getClass().getName(), "outputNumberedListWithHeader");
        List<String> output = new ArrayList<>();
        output.add(header);
        for (int i = 0; i < inputList.size(); i++) {
            output.add(((i + 1) + ". " + inputList.get(i).toString()));
        }
        LOGGER.fine(LOG_OUTPUT_NUMBERED_LIST);
        LOGGER.entering(getClass().getName(), "outputNumberedListWithHeader");
        return output;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        this.list.clear();
    }

    /**
     * Sorts the list according to custom comparator given in child class.
     */
    public abstract void sort();

    /**
     * Populates data into this list from the list's localStorage.
     */
    @Override
    public abstract void loadData() throws DataReadWriteException, CorruptedDataException, DateFormatException;

    /**
     * Saves the current list data into the list's localStorage.
     */
    @Override
    public abstract void saveData() throws DataReadWriteException;

    /**
     * View the list of items in string format for output.
     */
    public abstract List<String> viewList();

    /**
     * Checks the list of items with names that contain keyword.
     * @param keyword The keyword to be searched.
     * @return The list of string of items that contain keyword in their name.
     */
    public abstract List<String> containsKeyword(String keyword);
}
