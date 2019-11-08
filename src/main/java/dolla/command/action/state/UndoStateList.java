package dolla.command.action.state;

import dolla.ModeStringList;
import dolla.ui.ActionUi;

import java.util.EmptyStackException;
import java.util.Stack;

//@@author yetong1895
public class UndoStateList implements ModeStringList {
    private static Stack<State> entryStateList = new Stack<>();
    private static Stack<State> debtStateList = new Stack<>();
    private static Stack<State> limitStateList = new Stack<>();
    private static Stack<State> shortcutStateList = new Stack<>();

    /**
     * This method will push the input state onto the stack of State in this class with respect to the mode.
     * @param state the State to be pushed into the stack.
     * @param mode the mode that the program is in.
     */
    public static void addState(State state, String mode) {
        if (mode.equals(MODE_ENTRY)) {
            entryStateList.push(state);
        } else if (mode.equals(MODE_DEBT)) {
            debtStateList.push(state);
        } else if (mode.equals(MODE_LIMIT)) {
            limitStateList.push(state);
        } else if (mode.equals(MODE_SHORTCUT)) {
            shortcutStateList.push(state);
        }
    }

    /**
     * This method will get the State from the stack of State with respect to the mode.
     * @param mode the mode that the program is in.
     * @return state the state.
     */
    public static State getState(String mode) {
        String type = "undo";
        try {
            if (mode.equals(MODE_ENTRY)) {
                return entryStateList.pop();
            } else if (mode.equals(MODE_DEBT)) {
                return debtStateList.pop();
            } else if (mode.equals(MODE_LIMIT)) {
                return limitStateList.pop();
            } else if (mode.equals(MODE_SHORTCUT)) {
                return shortcutStateList.pop();
            }
        } catch (EmptyStackException e) {
            ActionUi.printEmptyStackError(type);
        }
        return null;
    }

    public static int getSize(String mode) {
        if (mode.equals(MODE_ENTRY)) {
            return entryStateList.size();
        } else if (mode.equals(MODE_DEBT)) {
            return debtStateList.size();
        } else if (mode.equals(MODE_LIMIT)) {
            return limitStateList.size();
        } else if (mode.equals(MODE_SHORTCUT)) {
            return shortcutStateList.size();
        }
        return -1;
    }

}
