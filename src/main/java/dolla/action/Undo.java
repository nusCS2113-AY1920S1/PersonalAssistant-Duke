package dolla.action;

import java.util.Stack;

/**
 * The type Undo. //todo: edit javadoc
 */
public class Undo {
    private static String userInput; //user inputs
    private static String command; //add, remove etc
    private static String undoInput; //reverse logic inputs
    private static String redoInput;
    private static String mode;
    private static int index;
    private static int prevPosition;

    private static Stack<String> undoEntryCommand = new Stack<>();
    private static Stack<String> undoDebtCommand = new Stack<>();
    private static Stack<String> undoLimitCommand = new Stack<>();

    /*
    public Undo(String userInput, String command, boolean isFromRedo) {
        Undo.userInput = userInput;
        Undo.command = command;
    }
     */

    /**
     * Remove command.
     *
     * @param mode  the mode
     * @param index the index
     */
    public static void removeCommand(String mode, int index) {
        Undo.mode = mode;
        Undo.index = index;
        remove();
    }

    /**
     * Add command.
     *
     * @param mode         the mode
     * @param userInput    the user input
     * @param prevPosition the prev position
     */
    public static void addCommand(String mode, String userInput, int prevPosition) {
        Undo.mode = mode;
        Undo.userInput = userInput;
        Undo.prevPosition = prevPosition;
        add();
    }

    /**
     * Remove.
     */
    public static void remove() {
        undoInput = "remove " + index;
        if (mode.equals("entry")) {
            undoEntryCommand.push(undoInput);
        } else if (mode.equals("debt")) {
            undoDebtCommand.push(undoInput);
        } else {
            undoLimitCommand.push(undoInput);
        }
    }

    /**
     * Add.
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
     * Process command string.
     *
     * @param mode the mode
     * @return the string
     */
    public static String processCommand(String mode) {
        if (mode.equals("entry")) {
            undoInput = undoEntryCommand.pop();
        } else if (mode.equals("debt")) {
            undoInput = undoDebtCommand.pop();
        } else {
            undoInput = undoDebtCommand.pop();
        }
        //String parser[] = undoInput.split(" ",3);
        //Redo.setRedoFlag(1);
        return undoInput;
    }

    /*
    public static String processUndo() { //if user input an Undo command
        //if size > 0
        undoInput = undoCommand.pop();
        String parser[] = undoInput.split(" ",3);
        Redo.setRedoFlag(1);

        if(parser[0].equals("add") || parser[0].equals("set")) {

            redoInput = "remove " + undoInput.substring(5);

        } else if(parser[0].equals("borrow") || parser[0].equals("owe")) {

            redoInput = "remove " + undoInput;

        } else if(parser[0].equals("remove")) {

            if(parser[1].equals("borrow") || parser[1].equals("owe")) {
                redoInput = undoInput.substring(8);
            } else if(parser[1].equals("saving") || parser[1].equals("budget")) {
                redoInput = "set " + undoInput.substring(8);
            } else {
                redoInput = "add " + undoInput.substring(8);
            }

        }
        Redo.setRedoInput(redoInput);
        return undoInput;
    }
     */
}
