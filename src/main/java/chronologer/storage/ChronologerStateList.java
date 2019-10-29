package chronologer.storage;

import chronologer.exception.ChronologerException;
import chronologer.task.Task;
import chronologer.ui.UiTemporary;

import java.util.ArrayList;

public class ChronologerStateList {
    private static ArrayList<Object> chronologerStateList = new ArrayList<>();
    private static Integer currentStatePointer = 0;

    public static void initialState(ArrayList<Task> listToStore){
        chronologerStateList.add(listToStore.clone());
    }

    /**
     * Function to store the current state.
     *
     */
    public static void addState(ArrayList<Task> listToStore){
        chronologerStateList.add(listToStore.clone());
        currentStatePointer += 1;
    }

    /**
     * Function to undo to previous state.
     *
     */
    public static ArrayList<Task> undo() throws ChronologerException {
        if (currentStatePointer == 0) {
            UiTemporary.printOutput("Sorry unable to undo further");
            throw new ChronologerException(ChronologerException.fileDoesNotExist());
        }
        else {
            currentStatePointer -= 1;
        }
        System.out.println(currentStatePointer);
        System.out.println(chronologerStateList.get(currentStatePointer).toString());
        return (ArrayList<Task>) chronologerStateList.get(currentStatePointer);
    }

    /**
     * Function to redo to a previous state that was undone.
     *
     */
    public static ArrayList<Task> redo()  throws ChronologerException {
        if (currentStatePointer == chronologerStateList.size() - 1) {
            UiTemporary.printOutput("Sorry unable to redo further");
            throw new ChronologerException(ChronologerException.fileDoesNotExist());
        }
        else {
            currentStatePointer += 1;
        }
        return (ArrayList<Task>) chronologerStateList.get(currentStatePointer);
    }
}
