package dolla.action;

import dolla.task.Entry;
import dolla.task.Record;
import dolla.ui.ActionUi;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Undo {
    //@@author yetong1895
    private static State state;
    private static ArrayList<Record> list;

    public static void receiveState(String mode) {
        state = StateList.getState(mode);
    }

    public static ArrayList<Record> processState(String mode) {
        receiveState(mode);
        if (mode.equals("entry")) {
            list = state.getEntryState();
        } else if (mode.equals("debt")) {
            list = state.getDebtState();
        } else if (mode.equals("limit")) {
            list = state.getLimitState();
        }
        return list;
    }




















}

