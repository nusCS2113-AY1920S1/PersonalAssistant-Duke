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

        if (isListCmd()) {
            return new ShowListCommand(mode);

        } else if (isAddEntryCmd()) {
            if (verifyAddCommand()) {
                return new AddEntryCommand(type, amount, description, date);
            } else {
                return new ErrorCommand();
            }

        } else if (isModifyCmd()) {
            if (verifyFullModifyCommand()) {
                return new InitialModifyCommand(inputArray[1]);
            } else if (verifyPartialModifyCommand()) {
                return new PartialModifyEntryCommand(modifyRecordNum, type, amount, description, date);
            } else {
                return new ErrorCommand();
            }

        } else if (isSortCmd()) {
            if (verifySort()) {
                return new SortCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }

        } else if (isSearchCmd()) {
            if (verifyEntrySearchCommand()) {
                return new SearchCommand(mode, inputArray[1], inputArray[2]);
            } else {
                return new ErrorCommand();
            }

        } else if (isRemoveCmd()) {
            if (verifyRemove()) {
                return new RemoveCommand(mode, inputArray[1]);
            } else {
                return new ErrorCommand();
            }

        } else if (isActionCmd()) {
            return new ActionCommand(mode, commandToRun);

        } else if (isCreateCmd()) {
            if (verifyShortcut()) {
                return new AddShortcutCommand(inputArray[1], mode);
            } else {
                return new ErrorCommand();
            }

        } else if (isExecuteCmd()) {
            if (verifyShortcut()) {
                return new ExecuteShortcutCommand(inputArray[1], mode);
            } else {
                return new ErrorCommand();
            }

        } else if (isListShortcutsCmd()) {
            return new ShowListCommand(MODE_SHORTCUT);
        } else {
            return invalidCommand();
        }
    }

    private boolean isListCmd() {
        return commandToRun.equals(ENTRY_COMMAND_LIST);
    }

    private boolean isCreateCmd() {
        return commandToRun.equals(SHORTCUT_COMMAND_CREATE);
    }

    private boolean isExecuteCmd() {
        return commandToRun.equals(SHORTCUT_COMMAND_EXECUTE);
    }

    private boolean isListShortcutsCmd() {
        return commandToRun.equals(SHORTCUT_COMMAND_LIST);
    }

}


