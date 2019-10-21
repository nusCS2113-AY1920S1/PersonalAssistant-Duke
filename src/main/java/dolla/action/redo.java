package dolla.action;

import java.util.Stack;

public class redo {
    private static int redoFlag = 0;
    private static String redoInput;
    private static Stack<String> redoCommand = new Stack<>();
    private static String userInput;
    private static String mode;
    private static int prevPosition;
    private static int index;

    private static Stack<String> redoEntryCommand = new Stack<>();
    private static Stack<String> redoDebtCommand = new Stack<>();
    private static Stack<String> redoLimitCommand = new Stack<>();

//    public redo(String mode, String userInput) {
//        redo.mode = mode;
//        redo.userInput = userInput;
//        add();
//    }

    public static void addCommand(String mode, String userInput) {
        redo.mode = mode;
        redo.userInput = userInput;
//        redo.prevPosition = prevPosition;
        add();
    }

    public static void removeCommand(String mode, int index) {
        redo.mode = mode;
        redo.index = index + 1;
        remove();
    }

    public static void add() {
        if(mode.equals("entry")) {
            redoEntryCommand.push(userInput);
        } else if(mode.equals("debt")) {
            redoDebtCommand.push(userInput);
        } else {
            redoLimitCommand.push(userInput);
        }
    }

    public static void remove() {
        redoInput = "remove " + index;
        if(mode.equals("entry")) {
            redoEntryCommand.push(redoInput);
        } else if(mode.equals("debt")) {
            redoDebtCommand.push(redoInput);
        } else {
            redoLimitCommand.push(redoInput);
        }
    }

    public static void redoReady(String mode) {
//        redoFlag = 1;
        if(mode.equals("entry")) {
            redoInput = redoEntryCommand.pop();
        } else if(mode.equals("debt")) {
            redoInput = redoDebtCommand.pop();
        } else {
            redoInput = redoLimitCommand.pop();
        }
    }

    public static String processRedo() {
//        if(redoFlag == 1) {
        System.out.println(redoInput);
            return redoInput;
//        } else {
//            return "sorry there is no command to redo";
//        }
    }

    public static void clearRedo() {
            redoCommand.clear();
    }

    public static void setRedoFlag(int redoFlag) {
        redo.redoFlag = redoFlag;
    }
}
