package duke.list;

import java.io.File;
import java.util.List;
import duke.exception.DukeException;

public abstract class DukeList<T extends DukeItem> {
    private static String STORAGE_DELIMITER = "\n\n";

    private File file;

    protected List<T> internalList;

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
     * Returns {@code internalList}.
     *
     * @return {@code internalList}.
     */
    public List<T> getInternalList() {
        return internalList;
    }

    /**
     * Indicates that the list has reached a new state for {@link #undo} {@link #redo}, and also
     * calls {@link #save}. This method should be called whenever the list is externally changed.
     */
    public void update() {

    }

    /**
     * Saves {@code internalList} into {@code file}.
     *
     * @throws DukeException if the file could not be saved to.
     */
    private void save() {

    }

    /**
     * Loads the data contained in {@code file} into {@code internalList} and updates {@code externalList},
     * overwriting any existing information.
     *
     * @throws DukeException if the file could not be accessed, or if the information in the file is invalid.
     */
    public void load() {

    }

    /**
     * Reverts the state of {@code internalList} some number of times to an earlier state.
     *
     * @param times the number of times to undo.
     * @throws DukeException if {@code internalList} has no earlier state.
     */
    public void undo(int times) {

    }

    /**
     * @see #undo
     * Forwards the state of {@code internalList} some number of times to a later state.
     *
     * @param times the number of times to redo.
     * @throws DukeException if {@code internalList} has no later state.
     */
    public void redo(int times) {

    }
}