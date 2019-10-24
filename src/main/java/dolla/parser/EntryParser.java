package dolla.parser;

import dolla.action.Repeat;
import dolla.command.Command;
import dolla.command.AddEntryCommand;
import dolla.command.AddActionCommand;
import dolla.command.ShowListCommand;
import dolla.command.ErrorCommand;
import dolla.command.InitialModifyCommand;
import dolla.command.SortCommand;
import dolla.command.SearchCommand;
import dolla.command.RemoveCommand;

public class EntryParser extends Parser {

    public EntryParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("entries")) { //show entry list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals("add")) {
            if (verifyAddCommand()) {
                String[] data = inputLine.split(" /on ");
                String[] desc = data[0].split(inputArray[2] + " ");
                description = desc[1];
                return processAdd();
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals("modify")) {
            if (verifyModifyCommand()) {
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
        } else if (commandToRun.equals("redo") || commandToRun.equals("undo") || commandToRun.equals("Repeat")) {
            return new AddActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }

    private Command processAdd() {
        Command addEntry;
        Repeat.setRepeatInput("entry", inputLine);
        if (undoFlag == 1) { //undo input
            addEntry = new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]),
                    description, date, prevPosition);
        } else if (redoFlag == 1) {
            addEntry = new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]),
                    description, date, -2);
        } else { //normal input, prePosition is -1
            addEntry = new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]),
                    description, date, -1);
        }
        return addEntry;
    }
}


