package dolla.command;

import dolla.command.action.state.*;
import dolla.model.DollaData;

import dolla.command.action.Redo;
import dolla.model.Record;
import dolla.ui.RemoveUi;

import java.util.ArrayList;

//@@author yetong1895
/**
 * RemoveCommand is a Command used to remove a Task from the TaskList.
 */
public class RemoveCommand extends Command {
    private String logNumStr;
    protected String mode;

    public RemoveCommand(String mode, String logNumStr) {
        this.mode = mode;
        this.logNumStr = logNumStr;
    }

    /**
     * THis method will remove the record with respect to the user input position and the mode.
     * @param dollaData dollaData
     */
    @Override
    public void execute(DollaData dollaData) {
        int recordNumInt;
        ArrayList<Record> recordList = dollaData.getRecordList(mode);
        try {
            if (mode.equals(MODE_ENTRY)) {
                UndoStateList.addState(new EntryState(recordList), mode);
            } else if (mode.equals(MODE_DEBT)) {
                UndoStateList.addState(new DebtState(recordList), mode);
            } else if (mode.equals(MODE_LIMIT)) {
                UndoStateList.addState(new LimitState(recordList), mode);
            } else if (mode.equals(MODE_SHORTCUT)) {
                UndoStateList.addState(new ShortcutState(recordList), mode);
            }
            Redo.clearRedoState(mode);
            recordNumInt = stringToInt(logNumStr) - 1;
            Record record = recordList.get(recordNumInt);
            RemoveUi.echoRemove(record.getRecordDetail(), mode);
            dollaData.removeFromRecordList(mode, recordNumInt);
        } catch (IndexOutOfBoundsException e) {
            RemoveUi.printNumberOfRecords(recordList.size());
        }
    }

    @Override
    public String getCommandInfo() {
        return mode;
    }
}
