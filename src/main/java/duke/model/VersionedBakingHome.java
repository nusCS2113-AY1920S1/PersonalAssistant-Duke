package duke.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code BakingHome} that keeps track of its own history.
 */
public class VersionedBakingHome extends BakingHome {

    private final List<ReadOnlyBakingHome> bakingHomeStateList;
    private int currentStatePointer;
    private boolean isTrackingEnabled = true;

    /**
     * Creates a {@code VersionedAddressBook} with an initial {@code ReadOnlyBakingHome}.
     */
    public VersionedBakingHome(ReadOnlyBakingHome initialState) {
        super(initialState);

        bakingHomeStateList = new ArrayList<>();
        bakingHomeStateList.add(new BakingHome(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code BakingHome} state at the end of the state list if version tracking is enabled.
     * If tracking is not enabled, does nothing.
     */
    public void commit() {
        if (isTrackingEnabled) {
            removeStatesAfterCurrentPointer();
            bakingHomeStateList.add(new BakingHome(this));
            currentStatePointer++;
        }
    }

    private void removeStatesAfterCurrentPointer() {
        bakingHomeStateList.subList(currentStatePointer + 1, bakingHomeStateList.size()).clear();
    }

    public void setVersionControl(boolean isEnabled) {
        this.isTrackingEnabled = isEnabled;
    }

    /**
     * Restores BakingHome to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(bakingHomeStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(bakingHomeStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has baking home states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has baking home states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < bakingHomeStateList.size() - 1;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Unable to redo.");
        }
    }

}
