package dolla.command.action;

import dolla.ModeStringList;
import dolla.command.action.state.LimitState;
import dolla.command.action.state.DebtState;
import dolla.command.action.state.EntryState;
import dolla.command.action.state.State;
import dolla.command.action.state.UndoStateList;
import dolla.model.Record;

import java.util.ArrayList;

//@@author yetong1895
public class Undo implements ModeStringList {
    private static State state;
    private static ArrayList<Record> list;

    /**
     * This method will get the state from the UndoStateList containing all the states.
     * @param mode the mode that the program is in.
     */
    public static void receiveUndoState(String mode) {
        state = UndoStateList.getState(mode);
    }

    /**
     * This method will add the current state to the UndoStateList containing all the states.
     * @param mode the mode that the program is in.
     * @param currStatelist the ArrayList containing the current state.
     */
    public static void addToStateList(String mode, ArrayList<Record> currStatelist) {
        if (mode.equals(MODE_ENTRY)) {
            UndoStateList.addState(new EntryState(currStatelist), mode);
        } else if (mode.equals(MODE_DEBT)) {
            UndoStateList.addState(new DebtState(currStatelist), mode);
        } else if (mode.equals(MODE_LIMIT)) {
            UndoStateList.addState(new LimitState(currStatelist), mode);
        }
    }

    /**
     * This method will return the state obtained from a state with respect to the mode.
     * @param mode the mode that the program is in.
     * @return list the ArrayList containing the undo state.
     */
    public static ArrayList<Record> processUndoState(String mode) {
        receiveUndoState(mode);
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




















}

