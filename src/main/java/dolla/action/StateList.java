package dolla.action;

import java.util.Stack;

public class StateList {
    private static Stack<State> entryStateList = new Stack<>();
    private static Stack<State> debtStateList = new Stack<>();
    private static Stack<State> limitStateList = new Stack<>();
    private static Stack<State> shortcutStateList = new Stack<>();

    public static void addState(State state, String mode) {
        if (mode.equals("entry")) {
            entryStateList.push(state);
            System.out.println(entryStateList.size());
        } else if (mode.equals("debt")) {
            debtStateList.push(state);
        } else if (mode.equals("limit")) {
            limitStateList.push(state);
        }
    }

    public static State getState(String mode) {
        if (mode.equals("entry")) {
            return entryStateList.pop();
        } else if (mode.equals("debt")) {
            return debtStateList.pop();
        } else if (mode.equals("limit")) {
            return limitStateList.pop();
        } else {
            return null;
        }
    }

}
