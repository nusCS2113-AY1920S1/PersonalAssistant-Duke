package dolla.action;

import dolla.command.Command;
import parser.MainParser;

import java.util.Stack;

public class undo {
    String userInput; //user inputs
    String command; //add, remove etc
    String undoInput; //reverse logic inputs
    static boolean redoCheck = false; //check if an undo is done.
    Stack<String> undoCommand = new Stack<>();

    public undo(String userInput, String command) {
        this.userInput = userInput;
        this.command = command;
    }

    public void addUndoCommand() {
        switch (command) {
        case("add"):
            undoInput = "remove" + userInput.substring(5);
            undoCommand.push(undoInput);
            redoCheck = false;
            break;
        case("remove"):
            undoInput = "add" + userInput.substring(8);
            undoCommand.push(undoInput);
            redoCheck = false;
            break;
        }
    }

    public String inputUndoCommand() { //if user input an undo command
        redoCheck = true;
        undoInput = undoCommand.pop();
        return undoInput;
    }

    public static boolean isRedoCheck() {
        return redoCheck;
    }

}
