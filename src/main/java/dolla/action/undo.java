package dolla.action;

import dolla.DollaData;
import dolla.task.LogList;

import java.util.ArrayList;
import java.util.Stack;

public class undo {
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

//    public undo(String userInput, String command, boolean isFromRedo) {
//        undo.userInput = userInput;
//        undo.command = command;
//    }
    public static void removeCommand(String mode, int index) {
        undo.mode = mode;
        undo.index = index;
        remove();
    }

    public static void addCommand(String mode, String userInput, int prevPosition) {
        undo.mode = mode;
        undo.userInput = userInput;
        undo.prevPosition = prevPosition;
        add();
    }

    public static void remove() {
        undoInput = "remove " + index;
        if(mode.equals("entry")) {
            undoEntryCommand.push(undoInput);
        } else if(mode.equals("debt")) {
            undoDebtCommand.push(undoInput);
        } else {
            undoLimitCommand.push(undoInput);
        }
    }

    public static void add() {
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

    public static String processCommand(String mode) {
        if(mode.equals("entry")) {
            undoInput = undoEntryCommand.pop();
        } else if(mode.equals("debt")) {
            undoInput = undoDebtCommand.pop();
        } else {
            undoInput = undoDebtCommand.pop();
        }
//        String parser[] = undoInput.split(" ",3);
//        redo.setRedoFlag(1);
        return undoInput;
    }


//    public static void addUndoCommand(boolean isFromRedo) {
//
//        if(isFromRedo == false) {
//            redo.setRedoFlag(0);
//        }
//        redo.checkFlag();
//        String parser[] = userInput.split(" ",3);
//
//        if(command.equals("add") || command.equals("set")  || command.equals("owe")) {
//
//            undoInput = "remove " + userInput.substring(5);
//            undoCommand.push(undoInput);
//
//        } else if(command.equals("borrow")) {
//
//            undoInput = "remove " + userInput;
//            undoCommand.push(undoInput).substring(8);
//
//        } else if(command.equals("remove")) {
//
//            if(parser[1].equals("borrow") || parser[1].equals("owe")) {
//                undoInput = userInput.substring(8);
//            } else if(parser[1].equals("saving") || parser[1].equals("budget")) {
//                undoInput = "set " + userInput.substring(8);
//            } else {
//                undoInput = "add " + userInput.substring(8);
//            }
//            undoCommand.push(undoInput);
//
//        }
//    }
//
//    public static String processUndo() { //if user input an undo command
//        //if size > 0
//        undoInput = undoCommand.pop();
//        String parser[] = undoInput.split(" ",3);
//        redo.setRedoFlag(1);
//
//        if(parser[0].equals("add") || parser[0].equals("set")) {
//
//            redoInput = "remove " + undoInput.substring(5);
//
//        } else if(parser[0].equals("borrow") || parser[0].equals("owe")) {
//
//            redoInput = "remove " + undoInput;
//
//        } else if(parser[0].equals("remove")) {
//
//            if(parser[1].equals("borrow") || parser[1].equals("owe")) {
//                redoInput = undoInput.substring(8);
//            } else if(parser[1].equals("saving") || parser[1].equals("budget")) {
//                redoInput = "set " + undoInput.substring(8);
//            } else {
//                redoInput = "add " + undoInput.substring(8);
//            }
//
//        }
//        redo.setRedoInput(redoInput);
//        return undoInput;
//    }

}
