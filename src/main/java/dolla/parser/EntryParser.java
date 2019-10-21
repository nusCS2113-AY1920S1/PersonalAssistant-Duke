package dolla.parser;

import dolla.command.*;

public class EntryParser extends Parser {
    private static int prevPosition;
    private static int undoFlag = 0;

    public EntryParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("entries")) { //show entry list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals("add")) {
            if (verifyAddCommand() == true) {
                String[] data = inputLine.split(" /on ");
                String[] desc = data[0].split(inputArray[2] + " ");
                description = desc[1];
                if(undoFlag == 1) {//undo input
                    undoFlag = 0;
                    return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]), description, date, prevPosition);
                } else {//normal input, prePosition is -1
                    return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]), description, date, -1);
                }
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals("modify")) {
            if (verifyModifyCommand() == true) {
                return new InitialModifyCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals("sort")) {
            return new SortCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("search")) {
            String component = inputArray[1];
            String content = inputArray[2];
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals("remove")) { //TODO: indexoutofbound exception
            return new RemoveCommand(mode, inputArray[1]);
        } else if (commandToRun.equals("redo") || commandToRun.equals("undo") || commandToRun.equals("repeat")) {
            return new AddActionCommand(mode, commandToRun);
        } else {
                return invalidCommand();
        }
    }

    public static void setPrePosition(int prePosition) {
        EntryParser.prevPosition = prePosition;
        undoFlag = 1;
    }
}


