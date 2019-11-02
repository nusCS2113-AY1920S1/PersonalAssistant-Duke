package dolla.action;

import dolla.ModeStringList;
import dolla.ui.ActionUi;

import java.util.EmptyStackException;
import java.util.Stack;

public class RedoStateList implements ModeStringList {
    private static Stack<State> entryStateList = new Stack<>();
    private static Stack<State> debtStateList = new Stack<>();
    private static Stack<State> limitStateList = new Stack<>();
    private static Stack<State> shortcutStateList = new Stack<>();

    public static void addState(State state, String mode) {
        if (mode.equals(MODE_ENTRY)) {
            entryStateList.push(state);
        } else if (mode.equals(MODE_DEBT)) {
            debtStateList.push(state);
        } else if (mode.equals(MODE_LIMIT)) {
            limitStateList.push(state);
        }
    }

    public static State getState(String mode) {
        String type = "redo";
        try {
            if (mode.equals(MODE_ENTRY)) {
                return entryStateList.pop();
            } else if (mode.equals(MODE_DEBT)) {
                return debtStateList.pop();
            } else if (mode.equals(MODE_LIMIT)) {
                return limitStateList.pop();
            }
        } catch (EmptyStackException e) {
            ActionUi.printEmptyStackError(type);
        }
        return null;
    }

    public static void clear(String mode) {
        if (mode.equals("entry")) {
            entryStateList.clear();
        } else if (mode.equals("debt")) {
            debtStateList.clear();
        } else if (mode.equals("limit")) {
            limitStateList.clear();
        }
    }
}
