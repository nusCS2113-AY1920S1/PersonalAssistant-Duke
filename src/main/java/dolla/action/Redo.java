package dolla.action;

import dolla.ModeStringList;
import dolla.task.Record;
import java.util.ArrayList;

public class Redo implements ModeStringList {
    private static State state;
    private static ArrayList<Record> list;

    public static void receiveRedoState(String mode) {
        state = RedoStateList.getState(mode);
    }

    public static void addToStateList(String mode, ArrayList<Record> currStatelist) {
        if (mode.equals(MODE_ENTRY)) {
            RedoStateList.addState(new EntryState(currStatelist), mode);
        } else if (mode.equals(MODE_DEBT)) {
            RedoStateList.addState(new DebtState(currStatelist), mode);
        } else if (mode.equals(MODE_LIMIT)) {
            RedoStateList.addState(new LimitState(currStatelist), mode);
        }
    }

    public static ArrayList<Record> processRedoState(String mode) {
        receiveRedoState(mode);
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

    public static void clearRedoState(String mode) {
        RedoStateList.clear(mode);
    }
}
