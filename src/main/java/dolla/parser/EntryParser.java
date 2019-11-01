package dolla.parser;

import dolla.Tag;
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
import dolla.command.modify.PartialModifyEntryCommand;
import dolla.task.Entry;

public class EntryParser extends Parser {

    private static final String ENTRY_COMMAND_REDO = "redo";
    private static final String ENTRY_COMMAND_UNDO = "undo";
    private static final String ENTRY_COMMAND_REPEAT = "repeat";

    public EntryParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_ENTRY;
    }

    @Override
    public Command parseInput() {

        if (commandToRun.equals(ENTRY_COMMAND_LIST)) { //show entry list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals(ENTRY_COMMAND_ADD)) {
            if (verifyAddCommand()) {
                Tag t = new Tag();
                Entry entry = new Entry(inputArray[1], stringToDouble(inputArray[2]), description, date);
                t.handleTag(inputLine, inputArray, entry);
                return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]),
                        description, date);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_MODIFY)) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else if (verifyPartialModifyCommand()) {
                return new PartialModifyEntryCommand(modifyRecordNum, type, amount, description, date);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(COMMAND_SORT)) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals("search")) {
            String component = inputArray[1];
            String content = inputArray[2];
            return new SearchCommand(mode, component, content);
        } else if (commandToRun.equals(COMMAND_REMOVE)) {
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }
        } else if (commandToRun.equals(ENTRY_COMMAND_REDO)
                || commandToRun.equals(ENTRY_COMMAND_UNDO)
                || commandToRun.equals(ENTRY_COMMAND_REPEAT)) {
            return new AddActionCommand(mode, commandToRun);
        } else {
            return invalidCommand();
        }
    }
}


