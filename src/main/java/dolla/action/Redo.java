package dolla.action;

import dolla.ModeStringList;

import dolla.action.state.LimitState;
import dolla.action.state.DebtState;
import dolla.action.state.EntryState;
import dolla.action.state.RedoStateList;
import dolla.action.state.State;
import dolla.task.Record;
import java.util.ArrayList;

//@@author yetong1895
public class Redo implements ModeStringList {
    private static State state;
    private static ArrayList<Record> list;

    /**
     * This method will get the state from the RedoStateList containing all the states.
     * @param mode the mode that the program is in.
     */
    public static void receiveRedoState(String mode) {
        state = RedoStateList.getState(mode);
    }

    /**
     * This method will add the current state to the RedoStateList containing all the states.
     * @param mode the mode that the program is in.
     * @param currStatelist the ArrayList containing the current state.
     */
    public static void addToStateList(String mode, ArrayList<Record> currStatelist) {
        if (mode.equals(MODE_ENTRY)) {
            RedoStateList.addState(new EntryState(currStatelist), mode);
        } else if (mode.equals(MODE_DEBT)) {
            RedoStateList.addState(new DebtState(currStatelist), mode);
        } else if (mode.equals(MODE_LIMIT)) {
            RedoStateList.addState(new LimitState(currStatelist), mode);
        }
    }

    /**
     * This method will return the state obtained from a state with respect to the mode.
     * @param mode the mode that the program is in.
     * @return list the ArrayList containing the redo state.
     */
    public static ArrayList<Record> processRedoState(String mode) {
        receiveRedoState(mode);
        if (state != null) {
            if (mode.equals(MODE_ENTRY)) {
                list = state.getEntryState();
            } else if (mode.equals(MODE_DEBT)) {
                list = state.getDebtState();
            } else if (mode.equals(MODE_LIMIT)) {
                list = state.getLimitState();
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * This method will call the clear method in RedoStateList.
     * @param mode the mode that the program is in.
     */
    public static void clearRedoState(String mode) {
        RedoStateList.clear(mode);
    }
}
