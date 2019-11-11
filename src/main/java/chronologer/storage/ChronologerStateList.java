package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.ui.UiMessageHandler;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class ChronologerStateList implements Serializable {

    private Stack<Object> chronologerUndoStack = new Stack<>();
    private Stack<Object> chronologerRedoStack = new Stack<>();
    private Storage storage1;
    private Storage storage2;
    private Storage storage3;
    private File version1;
    private File version2;
    private File version3;

    /**
     * Constructs the ChronologerStateList by passing in the version files.
     *
     * @param version1 Holds the file which will be utilised as the first state.
     * @param version2 Holds the file which will be utilised as the second state.
     * @param version3 Holds the file which will be utilised as the third state.
     */
    public ChronologerStateList(File version1, File version2, File version3) {
        this.storage1 = new Storage(version1);
        this.storage2 = new Storage(version2);
        this.storage3 = new Storage(version3);
        this.version1 = version1;
        this.version2 = version2;
        this.version3 = version3;
    }

    /**
     * Stores the current state based on the user's choice.
     */
    public void storeVersion(ArrayList<Task> listToStore, int version) throws ChronologerException {
        switch (version) {
        case 1:
            storage1.saveFile(listToStore);
            UiMessageHandler.outputMessage("Saved as state 1");
            break;
        case 2:
            storage2.saveFile(listToStore);
            UiMessageHandler.outputMessage("Saved as state 2");
            break;
        case 3:
            storage3.saveFile(listToStore);
            UiMessageHandler.outputMessage("Saved as state 3");
            break;
        default:
            UiMessageHandler.outputMessage("Please pick a valid state from 1 - 3");
        }
    }

    /**
     * Restores from the given state based on the user;s choice.
     */
    public ArrayList<Task> restoreVersion(ArrayList<Task> currentVersion, int version)
        throws ChronologerException {
        switch (version) {
        case 1:
            if (storage1.loadFile(version1).getTasks().size() != 0) {
                UiMessageHandler.outputMessage("Restored from state 1");
                return storage1.loadFile(version1).getTasks();
            }
            break;
        case 2:
            if (storage2.loadFile(version2).getTasks().size() != 0) {
                UiMessageHandler.outputMessage("Restored from state 2");
                return storage2.loadFile(version2).getTasks();
            }
            break;
        case 3:
            if (storage3.loadFile(version3).getTasks().size() != 0) {
                UiMessageHandler.outputMessage("Restored from state 3");
                return storage3.loadFile(version3).getTasks();
            }
            break;
        default:
            UiMessageHandler.outputMessage("Please pick a valid state from 1 - 3");
        }
        return currentVersion;
    }

    /**
     * Stores the current state.
     */
    public void addState(ArrayList<Task> listToStore) {
        chronologerUndoStack.push(SerializationUtils.clone(listToStore));
    }

    /**
     * Performs a undo to change to the previous state.
     */
    public ArrayList<Task> undo() throws ChronologerException {
        ArrayList<Task> toReturn;
        if (checkUndoSizeInvalid()) {
            throw new ChronologerException(ChronologerException.undoLimitHit());
        } else {
            chronologerRedoStack.push(chronologerUndoStack.pop());
            toReturn = (ArrayList<Task>) chronologerUndoStack.peek();
        }
        return (SerializationUtils.clone(toReturn));
    }

    /**
     * Performs a redo to change to a previous state that was undone.
     */
    public ArrayList<Task> redo() throws ChronologerException {
        ArrayList<Task> toReturn;
        if (checkRedoSizeInvalid()) {
            throw new ChronologerException(ChronologerException.redoLimitHit());
        } else {
            toReturn = (ArrayList<Task>) chronologerRedoStack.pop();
            chronologerUndoStack.push(SerializationUtils.clone(toReturn));
        }
        return (SerializationUtils.clone(toReturn));
    }

    /**
     * Function to determine if a redo can be performed.
     */
    private boolean checkRedoSizeInvalid () {
        return chronologerRedoStack.size() == 0;
    }

    /**
     * Function to determine if a undo can be performed.
     */
    private boolean checkUndoSizeInvalid () {
        return chronologerUndoStack.size() <= 1;
    }
}
