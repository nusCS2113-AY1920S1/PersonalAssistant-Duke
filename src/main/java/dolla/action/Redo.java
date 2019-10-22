package dolla.action;

import java.util.Stack;

/**
 * The type Redo. //TODO: edit javadoc
 */
public class Redo {
    private static int redoFlag = 0;
    private static String redoInput;
    private static Stack<String> redoCommand = new Stack<>();
    private static String userInput;

    /**
     * Sets redo flag.
     *
     * @param redoFlag the redo flag
     */
    public static void setRedoFlag(int redoFlag) {
        Redo.redoFlag = redoFlag;
    }

    /**
     * Sets redo input.
     *
     * @param redoInput the redo input
     */
    public static void setRedoInput(String redoInput) {
        Redo.redoInput = redoInput;
        redoCommand.push(Redo.redoInput);
    }

    /**
     * Process redo string.
     *
     * @return the string
     */
    public String processRedo() {
        userInput = redoCommand.pop();
        //Undo.addUndoCommand(true);
        return userInput;
    }

    /**
     * Check flag.
     */
    public static void checkFlag() {
        if (redoFlag == 0) {
            redoCommand.clear();
        }
    }
}
