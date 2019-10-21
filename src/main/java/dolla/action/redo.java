package dolla.action;

import java.util.Stack;

public class redo {
    private static int redoFlag = 0;
    private static String redoInput;
    private static Stack<String> redoCommand = new Stack<>();
    private static String userInput;
    private static String mode;

    private static Stack<String> redoEntryCommand = new Stack<>();
    private static Stack<String> redoDebtCommand = new Stack<>();
    private static Stack<String> redoLimitCommand = new Stack<>();

    public redo(String mode, String userInput) {
        redo.mode = mode;
        redo.userInput = userInput;
    }

    public static void inputs() {
        if(mode.equals("entry")) {
            redoEntryCommand.push(userInput);
        } else if(mode.equals("debt")) {
            redoDebtCommand.push(userInput);
        } else {
            redoLimitCommand.push(userInput);
        }
    }

    public static void redoReady(String mode) {
        redoFlag = 1;

        if(mode.equals("entry")) {
            redoInput = redoEntryCommand.pop();
        } else if(mode.equals("debt")) {
            redoInput = redoDebtCommand.pop();
        } else {
            redoInput = redoLimitCommand.pop();
        }
    }

    public String processRedo() {
        if(redoFlag == 1) {
            return redoInput;
        } else {
            return "sorry there is no command to redo";
        }
    }

//    public static void checkFlag() {
//        if(redoFlag == 0) {
//            redoCommand.clear();
//        }
//    }
//
    public static void setRedoFlag(int redoFlag) {
        redo.redoFlag = redoFlag;
    }
}
