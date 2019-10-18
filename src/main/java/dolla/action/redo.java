package dolla.action;

import java.util.Stack;

public class redo {
    private static int redoFlag = 0;
    private static String redoInput;
    private static Stack<String> redoCommand = new Stack<>();
    private static String userInput;

    public static void setRedoFlag(int redoFlag) {
        redo.redoFlag = redoFlag;
    }

    public static void setRedoInput(String redoInput) {
        redo.redoInput = redoInput;
        redoCommand.push(redo.redoInput);
    }

    public String processRedo() {
        userInput = redoCommand.pop();
        undo.addUndoCommand(true);
        return userInput;
    }

    public static void checkFlag() {
        if(redoFlag == 0) {
            redoCommand.clear();
        }
    }
}
