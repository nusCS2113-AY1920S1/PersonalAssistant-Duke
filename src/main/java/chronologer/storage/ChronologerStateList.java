package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.ui.UiTemporary;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class ChronologerStateList implements Serializable {
    private static Stack<Object> chronologerUndoStack = new Stack<>();
    private static Stack<Object> chronologerRedoStack = new Stack<>();

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
}
