package dolla.action;

import java.util.Stack;

/**
 * The type Redo. //TODO: edit javadoc
 */
public class Redo {
    private static String redoInput;
    private static Stack<String> redoCommand = new Stack<>();
    private static String userInput;
    private static String mode;
    private static int index;

    private static Stack<String> redoEntryCommand = new Stack<>();
    private static Stack<String> redoDebtCommand = new Stack<>();
    private static Stack<String> redoLimitCommand = new Stack<>();


    public static void addCommand(String mode, String userInput) {
        Redo.mode = mode;
        Redo.userInput = userInput;
        add();
    }

    public static void removeCommand(String mode, int index) {
        Redo.mode = mode;
        Redo.index = index + 1;
        remove();
    }

    private static void add() {
        if(mode.equals("entry")) {
            redoEntryCommand.push("add " + userInput);
        } else if(mode.equals("debt")) {
            redoDebtCommand.push(userInput);
        } else {
            redoLimitCommand.push(userInput);
        }
    }

    private static void remove() {
        redoInput = "remove " + index + "|redo";
        if(mode.equals("entry")) {
            redoEntryCommand.push(redoInput);
        } else if(mode.equals("debt")) {
            redoDebtCommand.push(redoInput);
        } else {
            redoLimitCommand.push(redoInput);
        }
    }

    public static void redoReady(String mode) {
        if(mode.equals("entry")) {
            redoInput = redoEntryCommand.pop();
        } else if(mode.equals("debt")) {
            redoInput = redoDebtCommand.pop();
        } else {
            redoInput = redoLimitCommand.pop();
        }
    }

    public static String processRedo() {
            return redoInput;
    }

    public static void clearRedo(String mode) {
        if(mode.equals("entry")) {
            redoEntryCommand.clear();
        } else if(mode.equals("debt")) {
            redoDebtCommand.clear();
        } else {
            redoLimitCommand.clear();
        }
    }
}
