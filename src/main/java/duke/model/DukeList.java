package duke.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;
import javafx.collections.ObservableList;

/**
 * The generic parent list of all lists in Duke, which are responsible for saving their own information
 * and have undo and redo capabilities.
 *
 * @param <T> The {@code DukeItem} contained in the list.
 */
abstract class DukeList<T extends DukeItem> {
    private static String STORAGE_DELIMITER = "\n\n";

    // private final File file;
    private final String itemName;
    private Stack<byte[]> undoStates;
    private byte[] currentState;
    private Stack<byte[]> redoStates;

    protected List<T> internalList;
    protected ObservableList<T> externalList;


    /**
     * Creates a new {@code DukeList}, which saves its data to a file {@code file}.
     *
     * @param file     The file to save to.
     * @param itemName the name of the item that populates the list.
     * @throws DukeException if the file cannot be created or read.
     */
    /*
    public DukeList(File file, String itemName) throws DukeException {
        this.file = file;
        this.itemName = itemName;
        internalList = new ArrayList<T>();
        load();

        undoStates = new Stack<byte[]>();
        currentState = toByteArray(internalList);
        redoStates = new Stack<byte[]>();
    }
     */

    public DukeList(List<T> internalList, String itemName) {
        this.itemName = itemName;
        this.internalList = internalList;

        undoStates = new Stack<byte[]>();
        currentState = toByteArray(internalList);
        redoStates = new Stack<byte[]>();
    }

    /**
     * Updates, then returns  {@code externalList}.
     * {@code externalList} should be updated based on filter, sort and view which are implemented
     * by the subclasses.
     *
     * @return the up-to-date externalList.
     */
    public abstract ObservableList<T> getExternalList();

    public abstract List<T> getInternalList();

    public abstract void setSortCriteria(String sortCriteria) throws DukeException;

    public abstract void setFilterCriteria(String filterCriteria) throws DukeException;

    public abstract void setViewScope(String viewScope, int previous) throws DukeException;

    public abstract List<T> sort(List<T> currentList);

    public abstract List<T> filter(List<T> currentList);

    public abstract List<T> view(List<T> currentList);

    /**
     * Adds an item into {@code internalList}.
     *
     * @param item the item to add.
     */
    public void add(T item) {
        internalList.add(item);
    }

    /**
     * Returns an item referenced using its index in {@code externalList}.
     *
     * @param index the index of the item in @{code externalList}.
     * @return the item.
     * @throws DukeException if the index is out of bounds.
     */
    public T get(int index) throws DukeException {
        if (index < 1 || index > externalList.size()) {
            throw new DukeException(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, itemName, index));
        }
        return externalList.get(index - 1);
    }

    /**
     * Returns the number of items in {@code internalList}.
     *
     * @return the number of items in {@code internalList}.
     */
    public int internalSize() {
        return internalList.size();
    }

    /**
     * Removes an item from {@code internalList} using its index in {@code externalList}.
     *
     * @param index the index of the item to in {@code externalList}.
     * @throws DukeException if the index is out of bounds.
     */
    public void remove(int index) throws DukeException {
        internalList.remove(get(index));
    }

    /**
     * Removes all items from {@code internalList}.
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Calling this method indicates that {@code internalList} or one of its members has changed,
     * that the file should be updated, and that the state has progressed such that all {@code redoStates}
     * are now invalid and should be discarded.
     *
     * @throws DukeException if saving was unsuccessful.
     */
    /*
    public void update() throws DukeException {
        save();
        undoStates.push(currentState);
        currentState = toByteArray(internalList);
        redoStates.clear();
    }
    */
    /**
     * Saves {@code internalList} into {@code file}.
     *
     * @throws DukeException if the file could not be saved to.
     */
    /*
    private void save() throws DukeException {
        try {
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file)) {
                for (T item : internalList) {
                    fileWriter.write(item.toStorageString());
                    fileWriter.write(STORAGE_DELIMITER);
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SAVE_FILE_FAILED, file.getPath()));
        }
    }
    */

    /**
     * Loads the data contained in {@code file} into {@code internalList} and updates {@code externalList},
     * overwriting any existing information.
     *
     * @throws DukeException if the file could not be accessed, or if any information in the file is invalid.
     */
    /*
    public void load() throws DukeException {
        try {
            file.createNewFile();
            try (Scanner fileReader = new Scanner(file).useDelimiter(STORAGE_DELIMITER)) {
                while (fileReader.hasNext()) {
                    internalList.add(itemFromStorageString(fileReader.next()));
                }
            }
        } catch (IOException | DukeException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_LOAD_FILE_FAILED, file.getPath()));
        }
    }

     */

    /**
     * Returns an item from its storage string. Although this method is present in the item builders,
     * it is declared here to make it easier to implement (otherwise requires reflection).
     *
     * @param storageString the storage string of the item.
     * @return the item.
     * @throws DukeException if the item could not be created from the storage string.
     */
    // protected abstract T itemFromStorageString(String storageString) throws DukeException;


    /**
     * Reverts the state of {@code internalList} some number of times to an earlier state.
     *
     * @param times the number of times to undo.
     * @return the number of times actually undone.
     * @throws DukeException if the changes could not be saved.
     */
    /*
    public int undo(int times) throws DukeException {
        int actualTimes = 0;
        for (actualTimes = 0; actualTimes < times && !undoStates.isEmpty(); actualTimes++) {
            redoStates.push(currentState);
            currentState = undoStates.pop();
        }

        internalList = fromByteArray(currentState);
        save();

        return actualTimes;
    }
    */

    /**
     * Forwards the state of {@code internalList} some number of times to a later state.
     *
     * @param times the number of times to redo.
     * @return the number of times actually undone.
     * @throws DukeRuntimeException if the changes could not be saved.
     * @see #undo
     */
    /*
    public int redo(int times) throws DukeException {
        int actualTimes = 0;
        for (actualTimes = 0; actualTimes < times && !redoStates.isEmpty(); actualTimes++) {
            undoStates.push(currentState);
            currentState = redoStates.pop();
        }

        internalList = fromByteArray(currentState);
        save();

        return actualTimes;
    }
     */

    /**
     * Taken from https://stackoverflow.com/a/30968827
     * Converts the current state of {@code internalList} into a byte array so that it can be restored later.
     *
     * @return the byte array of the current {@code internalList}.
     */
    private byte[] toByteArray(List<T> list) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(list);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new DukeRuntimeException("Failed to create byte array from list.", e);
        }
    }

    /**
     * Taken from https://stackoverflow.com/a/30968827
     * Returns a list corresponding to a previous state of {@code internalList}.
     * Casting to {@code List<T>} causes the warning. As the code is self-contained, there is no risk of
     * the object in {@code bytes} not being one of type {@code List<T>}.
     *
     * @param bytes a byte array corresponding to a previous state of {@code internalList}.
     * @return the previous state of {@code internalList}.
     */
    @SuppressWarnings("unchecked")
    private List<T> fromByteArray(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis)) {
            return (List<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DukeRuntimeException("Failed to load list from byte array.", e);
        }
    }
}