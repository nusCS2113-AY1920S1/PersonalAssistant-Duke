package dolla.action;

import java.util.Stack;

public class Undo {
    private static String userInput; //user inputs
    private static String undoInput; //reverse logic inputs
    private static String mode;
    private static int index;
    private static int prevPosition;

    private static Stack<String> undoEntryCommand = new Stack<>();
    private static Stack<String> undoDebtCommand = new Stack<>();
    private static Stack<String> undoLimitCommand = new Stack<>();

    /**
     * This method will process a "remove" command.
     * @param mode  the mode that the user is in.
     * @param index the index of the removing string.
     */
    public static void removeCommand(String mode, int index) {
        Undo.mode = mode;
        Undo.index = index;
        remove();
    }

    /**
     * This method will process a "add" command.
     * @param mode         the mode that the user is in.
     * @param userInput    the input from the user.
     * @param prevPosition the previous position of a deleted input.
     */
    public static void addCommand(String mode, String userInput, int prevPosition) {
        Undo.mode = mode;
        Undo.userInput = userInput;
        Undo.prevPosition = prevPosition;
        add();
    }

    /**
     * This method will push the "remove" command into the respective stack
     * depending on the current mode. The "/undo" serve as an indication that
     * this command come from "undo".
     */
    public static void remove() {
        undoInput = "remove " + index + "/undo";
        if (mode.equals("entry")) {
            undoEntryCommand.push(undoInput);
        } else if (mode.equals("debt")) {
            undoDebtCommand.push(undoInput);
        } else {
            undoLimitCommand.push(undoInput);
        }
    }

    /**
     * This method will push the "add" command into the respective stack
     * depending on the current mode. The prevPosition represents the
     * previous position that the deleted string is at.
     */
    public static void add() {
        if (mode.equals("entry")) {
            undoInput = prevPosition + " " + "add " + userInput;
            undoEntryCommand.push(undoInput);
        } else if (mode.equals("debt")) {
            undoInput = prevPosition + " " + userInput;
            undoDebtCommand.push(undoInput);
        } else {
            undoInput = prevPosition + " " + "set " + userInput;
            undoLimitCommand.push(undoInput);
        }
    }

    /**
     * This method will return the undoInput.
     * @param mode the mode that the user is in.
     * @return undoInput is the String that serve as an undo input.
     */
    public static String processCommand(String mode) {
        if (mode.equals("entry")) {
            undoInput = undoEntryCommand.pop();
        } else if (mode.equals("debt")) {
            undoInput = undoDebtCommand.pop();
        } else {
            undoInput = undoDebtCommand.pop();
        }
        return undoInput;
    }
}

