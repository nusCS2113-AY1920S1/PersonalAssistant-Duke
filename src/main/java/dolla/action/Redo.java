package dolla.action;

import java.util.Stack;

public class Redo {
    private static String redoInput;
    private static Stack<String> redoCommand = new Stack<>();
    private static String userInput;
    private static String mode;
    private static int index;

    private static Stack<String> redoEntryCommand = new Stack<>();
    private static Stack<String> redoDebtCommand = new Stack<>();
    private static Stack<String> redoLimitCommand = new Stack<>();


    /**
     * This method will process a "add" command.
     * @param mode      the mode that the user is in.
     * @param userInput the input from the user.
     */
    public static void addCommand(String mode, String userInput) {
        Redo.mode = mode;
        Redo.userInput = userInput;
        add();
    }

    /**
     * This method will process a "remove" command.
     * @param mode  the mode that the user is in.
     * @param index the index of the removing string.
     */
    public static void removeCommand(String mode, int index) {
        Redo.mode = mode;
        Redo.index = index + 1;
        remove();
    }

    /**
     * This method will push the "add" command into the respective stack
     * depending on the current mode.
     */
    private static void add() {
        if (mode.equals("entry")) {
            redoEntryCommand.push("add " + userInput);
        } else if (mode.equals("debt")) {
            redoDebtCommand.push(userInput);
        } else {
            redoLimitCommand.push(userInput);
        }
    }

    /**
     * This method will push the "remove" command into the respective stack
     * depending on the current mode. The "|redo" serve as an indication that
     * this command come from "redo".
     */
    private static void remove() {
        redoInput = "remove " + index + "|redo";
        if (mode.equals("entry")) {
            redoEntryCommand.push(redoInput);
        } else if (mode.equals("debt")) {
            redoDebtCommand.push(redoInput);
        } else {
            redoLimitCommand.push(redoInput);
        }
    }

    /**
     * This method will set the redoInput to the latest redoXcommand
     * with respect to the mode that the user is in.
     * @param mode the mode that the user is in.
     */
    public static void redoReady(String mode) {
        if (mode.equals("entry")) {
            redoInput = redoEntryCommand.pop();
        } else if (mode.equals("debt")) {
            redoInput = redoDebtCommand.pop();
        } else {
            redoInput = redoLimitCommand.pop();
        }
    }

    /**
     * This method will return the redoInput.
     * @return redoInput a string that serve as a redo input.
     */
    public static String processRedo() {
        return redoInput;
    }

    /**
     * This method will clear the respective stack with respect to
     * the mode that the user is currently in.
     * @param mode the mode that the user is in.
     */
    public static void clearRedo(String mode) {
        if (mode.equals("entry")) {
            redoEntryCommand.clear();
        } else if (mode.equals("debt")) {
            redoDebtCommand.clear();
        } else {
            redoLimitCommand.clear();
        }
    }
}
