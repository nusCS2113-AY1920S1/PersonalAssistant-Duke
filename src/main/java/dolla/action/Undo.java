package dolla.action;

import dolla.ModeStringList;
import dolla.task.Record;

import java.util.ArrayList;

public class Undo implements ModeStringList {
    //@@author yetong1895
    private static State state;
    private static ArrayList<Record> list;

    public static void receiveUndoState(String mode) {
        state = UndoStateList.getState(mode);
    }

    public static void addToStateList(String mode, ArrayList<Record> currStatelist) {
        if (mode.equals(MODE_ENTRY)) {
            UndoStateList.addState(new EntryState(currStatelist), mode);
        } else if (mode.equals(MODE_DEBT)) {
            UndoStateList.addState(new DebtState(currStatelist), mode);
        } else if (mode.equals(MODE_LIMIT)) {
            UndoStateList.addState(new LimitState(currStatelist), mode);
        }
    }

    public static ArrayList<Record> processUndoState(String mode) {
        receiveUndoState(mode);
        if(state != null) {
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

