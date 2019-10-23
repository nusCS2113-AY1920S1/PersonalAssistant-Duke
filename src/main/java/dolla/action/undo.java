package dolla.action;

import dolla.DollaData;
import dolla.task.LogList;

import java.util.ArrayList;
import java.util.Stack;

public class undo {
    private static String userInput; //user inputs
    private static String undoInput; //reverse logic inputs
    private static String mode;
    private static int index;
    private static int prevPosition;

    private static Stack<String> undoEntryCommand = new Stack<>();
    private static Stack<String> undoDebtCommand = new Stack<>();
    private static Stack<String> undoLimitCommand = new Stack<>();

    /**
     *
     * @param mode the mode
     * @param userInput
     * @param prevPosition
     */
    public static void addCommand(String mode, String userInput, int prevPosition) { //if input is remove
        undo.mode = mode;
        undo.userInput = userInput;
        undo.prevPosition = prevPosition;
        add();
    }

    public static void removeCommand(String mode, int index) { //if input is add
        undo.mode = mode;
        undo.index = index;
        remove();
    }

    private static void add() {
        if(mode.equals("entry")) {
            undoInput = prevPosition + " " + "add " + userInput;
            undoEntryCommand.push(undoInput);
        } else if(mode.equals("debt")) {
            undoInput = prevPosition + " " + userInput;
            undoDebtCommand.push(undoInput);
        } else {
            undoInput = prevPosition + " " + "set " + userInput;
            undoLimitCommand.push(undoInput);
        }
    }

    private static void remove() {
        undoInput = "remove " + index + "/undo";//1 to check if the remove is from undo command.
        if(mode.equals("entry")) {
            undoEntryCommand.push(undoInput);
        } else if(mode.equals("debt")) {
            undoDebtCommand.push(undoInput);
        } else {
            undoLimitCommand.push(undoInput);
        }
    }

    public static String processCommand(String mode) {
        if(mode.equals("entry")) {
            undoInput = undoEntryCommand.pop();
        } else if(mode.equals("debt")) {
            undoInput = undoDebtCommand.pop();
        } else {
            undoInput = undoDebtCommand.pop();
        }
        return undoInput;
    }
}
