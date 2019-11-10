package dolla.parser;

import dolla.command.ActionCommand;
import dolla.command.AddEntryCommand;
import dolla.command.AddShortcutCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ExecuteShortcutCommand;
import dolla.command.RemoveCommand;
import dolla.command.SearchCommand;
import dolla.command.ShowListCommand;
import dolla.command.SortCommand;
import dolla.command.modify.InitialModifyCommand;
import dolla.command.modify.PartialModifyEntryCommand;

public class EntryParser extends Parser {

    public EntryParser(String inputLine) {
        super(inputLine);
        this.mode = MODE_ENTRY;
    }

    @Override
    public Command parseInput() {

        if (commandToRun.equals(ENTRY_COMMAND_LIST)) {
            return new ShowListCommand(mode);

        } else if (commandToRun.equals(ENTRY_COMMAND_ADD)) {
            if (verifyAddCommand()) {
                return new AddEntryCommand(type, amount, description, date);
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

        } else if (commandToRun.equals(COMMAND_SEARCH)) {
            if (verifyEntrySearchCommand()) {
                return new SearchCommand(mode, inputArray[1], inputArray[2]);
            } else {
                return new ErrorCommand();
            }

        } else if (commandToRun.equals(COMMAND_REMOVE)) {
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }

        } else if (commandToRun.equals(COMMAND_REDO)
                || commandToRun.equals(COMMAND_UNDO)
                || commandToRun.equals(COMMAND_REPEAT)) {
            return new ActionCommand(mode, commandToRun);

        } else if (commandToRun.equals(SHORTCUT_COMMAND_CREATE)) {
            if (verifyShortcut()) {
                return new AddShortcutCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }

        } else if (commandToRun.equals(SHORTCUT_COMMAND_EXECUTE)) {
            if (verifyShortcut()) {
                return new ExecuteShortcutCommand(inputArray[1]);
            } else {
                return new ErrorCommand();
            }

        } else if (commandToRun.equals(SHORTCUT_COMMAND_LIST)) {
            return new ShowListCommand(mode);
        } else {
            return invalidCommand();
        }
    }
}


