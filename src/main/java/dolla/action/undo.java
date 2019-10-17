package dolla.action;

import dolla.command.Command;
import parser.MainParser;

import java.util.Stack;

public class undo {
    String userInput; //user inputs
    String command; //add, remove etc
    String undoInput; //reverse logic inputs
    String redoInput;
    String mode;
//    static int redoFlag = 0; //check if an undo is done.
    private Stack<String> undoCommand = new Stack<>();

    public undo(String userInput, String command) {
        this.userInput = userInput;
        this.command = command;
    }

    public void addUndoCommand() {
        redo.setRedoFlag(0);
        redo.checkFlag();

        if(command.equals("add") || command.equals("set")) {

            undoInput = "remove" + userInput.substring(5);
            undoCommand.push(undoInput);

        } else if(command.equals("borrow") || command.equals("owe")) {

            undoInput = "remove" + userInput.substring(5);
            undoCommand.push(undoInput);

        } else if(command.equals("remove")) {

            String parser[] = userInput.split(" ",3);
            if(parser[1].equals("borrow") || parser[1].equals("owe")) {
                undoInput = userInput.substring(8);
            } else if(parser[1].equals("saving") || parser[1].equals("budget")) {
                undoInput = "set" + userInput.substring(8);
            } else {
                undoInput = "add" + userInput.substring(8);
            }
            undoCommand.push(undoInput);

        }
    }

    public String processUndo() { //if user input an undo command
        undoInput = undoCommand.pop();
        String parser[] = undoInput.split(" ",3);
        redo.setRedoFlag(1);

        if(parser[0].equals("add") || parser[0].equals("set")) {
            redoInput = "remove" + undoInput.substring(5);
        }
//        switch(parser[0]) {
//            case("add"):
//                redoInput = "remove" + undoInput.substring(5);
//                break;
//            case("remove"):
//                redoInput = "add" + undoInput.substring(8);
//                break;
//        }
        redo.setRedoInput(redoInput);
        return undoInput;
    }

}
