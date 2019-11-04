package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.ui.UiTemporary;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class ChronologerStateList implements Serializable {


    private static Stack<Object> chronologerUndoStack = new Stack<>();
    private static Stack<Object> chronologerRedoStack = new Stack<>();
    private static String filePath1 = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version1";
    private static String filePath2 = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version2";
    private static String filePath3 = System.getProperty("user.dir") + "/src/ChronologerDatabase/Version3";
    private static File file1 = new File(filePath1);
    private static File file2 = new File(filePath2);
    private static File file3 = new File(filePath3);
    private static Storage storage1 = new Storage(file1);
    private static Storage storage2 = new Storage(file2);
    private static Storage storage3 = new Storage(file3);

    /**
     * Function to store the current state.
     *
     */
    public static void storeVersion(ArrayList<Task> listToStore, int version) throws ChronologerException {
        switch (version) {
        case 1:
            storage1.saveFile(listToStore);
            UiTemporary.printOutput("Saved as state 1");
            break;
        case 2:
            storage2.saveFile(listToStore);
            UiTemporary.printOutput("Saved as state 2");
            break;
        case 3:
            storage3.saveFile(listToStore);
            UiTemporary.printOutput("Saved as state 3");
            break;
        default:
            UiTemporary.printOutput("Please pick a valid state from 1 - 3");
        }
    }

    /**
     * Function to store the current state.
     *
     */
    public static ArrayList<Task> restoreVersion(ArrayList<Task> currentVersion, int version)
        throws ChronologerException {
        switch (version) {
        case 1:
            if (storage1.loadFile(file1).getTasks().size() != 0) {
                UiTemporary.printOutput("Restored from state 1");
                return storage1.loadFile(file1).getTasks();
            }
            break;
        case 2:
            if (storage2.loadFile(file2).getTasks().size() != 0) {
                UiTemporary.printOutput("Restored from state 2");
                return storage2.loadFile(file2).getTasks();
            }
            break;
        case 3:
            if (storage3.loadFile(file3).getTasks().size() != 0) {
                UiTemporary.printOutput("Restored from state 3");
                return storage3.loadFile(file3).getTasks();
            }
            break;
        default:
            UiTemporary.printOutput("Please pick a valid state from 1 - 3");
        }
        return currentVersion;
    }

    /**
     * Function to store the current state.
     *
     */
    public static void addState(ArrayList<Task> listToStore) {
        chronologerUndoStack.push(SerializationUtils.clone(listToStore));
    }

    /**
     * Function to undo to previous state.
     */
    public static ArrayList<Task> undo() throws ChronologerException {
        ArrayList<Task> toReturn;
        if (chronologerUndoStack.size() <= 1) {
            UiTemporary.printOutput("Sorry unable to undo further");
            throw new ChronologerException(ChronologerException.fileDoesNotExist());
        } else {
            chronologerRedoStack.push(chronologerUndoStack.pop());
            toReturn = (ArrayList<Task>) chronologerUndoStack.peek();
        }
        return (SerializationUtils.clone(toReturn));
    }

    /**
     * Function to redo to a previous state that was undone.
     *
     */
    public static ArrayList<Task> redo()  throws ChronologerException {
        ArrayList<Task> toReturn;
        if (chronologerRedoStack.size() == 0) {
            UiTemporary.printOutput("Sorry unable to redo further");
            throw new ChronologerException(ChronologerException.fileDoesNotExist());
        } else {
            toReturn = (ArrayList<Task>) chronologerRedoStack.pop();
            chronologerUndoStack.push(SerializationUtils.clone(toReturn));
        }
        return (SerializationUtils.clone(toReturn));
    }

    public static void storeUndoRedo() {
    }
}
