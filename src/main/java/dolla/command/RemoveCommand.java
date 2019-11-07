package dolla.command;

import dolla.model.DollaData;

import dolla.command.action.Redo;
import dolla.command.action.state.DebtState;
import dolla.command.action.state.EntryState;
import dolla.command.action.state.LimitState;
import dolla.command.action.state.UndoStateList;
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
     * Removes a task from the specified TaskList.
     * <p>
     *     The method first converts taskNumStr into an int. It will then check if a task
     *     corresponding to that number exists in the specified TaskList and subsequently
     *     remove that task if so.
     * </p>
     * <p>
     *     If taskNumStr is not an int, the method will return without doing anything.
     * </p>
     * <p>
     *     If taskNumInt does not correspond to any task in the specified TaskList, an
     *     alert is printed to the user, and the method will return.
     * </p>
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
            }
            Redo.clearRedoState(mode);
            recordNumInt = stringToInt(logNumStr) - 1;
            Record record = recordList.get(recordNumInt);
            RemoveUi.echoRemove(record.getRecordDetail());
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
