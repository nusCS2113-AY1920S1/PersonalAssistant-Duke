package list;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Stack;

import exception.DukeException;

public abstract class DukeList<T extends DukeItem> {
    private static String STORAGE_DELIMITER = "\n\n";

    private File file;
    private Stack<byte[]> undoStates;
    private byte[] currentState;
    private Stack<byte[]> redoStates;

    protected List<T> internalList;
    protected List<T> externalList;

    /**
     * Creates a new {@code DukeList}, which saves its data to a file {@code file}.
     *
     * @param file The file to save to.
     */
    public DukeList(File file) {
        this.file = file;
        load();
    }

    /**
     * Updates, then returns the externalList.
     * The externalList should be updated based on filter, sort and view which are implemented
     * by the subclasses.
     *
     * @return the up-to-date externalList.
     */
    public abstract List<T> getExternalList();

    /**
     * Adds an item into {@code internalList}.
     *
     * @param item the item to add.
     */
    public void add(T item) {

    }

    /**
     * Returns an item referenced using its index in {@code externalList}.
     *
     * @param index the index of the item in @{code externalList}.
     * @return the item.
     * @throws DukeException if the index is out of bounds.
     */
    public T get(int index) throws DukeException {
        try {
            return externalList.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("TODO"); // todo: update DukeException
        }
    }

    /**
     * Removes an item from {@code internalList} using its index in {@code externalList}.
     * @param index the index of the item to in {@code externalList}.
     * @throws DukeException if the index is out of bounds.
     */
    public void remove(int index) throws DukeException {
        internalList.remove(get(index));
    }

    /**
     * Indicates that the list has reached a new state for {@link #undo} {@link #redo}, and also
     * calls {@link #save}. This method should be called whenever the list is changed.
     */
    public void update() throws DukeException {
        save();
        undoStates.push(currentState);
        currentState = toByteArray();
        redoStates.clear();
    }

    /**
     * Saves {@code internalList} into {@code file}.
     *
     * @throws DukeException if the file could not be saved to.
     */
    private void save() throws DukeException {

    }

    /**
     * Loads the data contained in {@code file} into {@code internalList} and updates {@code externalList},
     * overwriting any existing information.
     *
     * @throws DukeException if the file could not be accessed, or if the information in the file is invalid.
     */
    public void load() throws DukeException {

    }

    /**
     * Reverts the state of {@code internalList} some number of times to an earlier state.
     *
     * @param times the number of times to undo.
     * @throws DukeException if {@code internalList} has no earlier state.
     */
    public void undo(int times) throws DukeException {
        if (undoStates.isEmpty()) {
            throw new DukeException("TODO"); // todo: update DukeException
        }

        for (int i = 0; i < times && !undoStates.isEmpty(); i++) {
            redoStates.push(currentState);
            currentState = undoStates.pop();
        }

        fromByteArray(currentState);
        save();
    }

    /**
     * @see #undo
     * Forwards the state of {@code internalList} some number of times to a later state.
     *
     * @param times the number of times to redo.
     * @throws DukeException if {@code internalList} has no later state.
     */
    public void redo(int times) throws DukeException {
        if (redoStates.isEmpty()) {
            throw new DukeException("TODO"); // todo: update DukeException
        }

        for (int i = 0; i < times && !redoStates.isEmpty(); i++) {
            undoStates.push(currentState);
            currentState = redoStates.pop();
        }

        fromByteArray(currentState);
        save();
    }

    /**
     * Taken from https://stackoverflow.com/a/30968827
     * Converts the current state of {@code internalList} into a byte array so that it can be restored later.
     *
     * @return the byte array of the current {@code internalList}.
     * @throws DukeException if an IO error occurs; this should never happen due to
     * the self-contained nature of this function.
     */
    private byte[] toByteArray() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(internalList);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new DukeException("TODO"); // todo: update DukeException
        }
    }

    /**
     * Taken from https://stackoverflow.com/a/30968827
     * Restores an earlier state of {@code internalList}.
     * Casting to {@code List<T>} causes the warning. As the code is self-contained, there is no risk of
     * the object in {@code bytes} not being one of type {@code List<T>}.
     *
     * @param bytes a byte array corresponding to an earlier state of {@code internalList}.
     * @throws DukeException if an {@code IOException} or {@code ClassNotFoundException} occurs;
     * this should never happen due to the self-contained nature of this function.
     */
    @SuppressWarnings("unchecked")
    private void fromByteArray(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            internalList = (List<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DukeException("TODO"); // todo: update DukeException
        }
    }
}