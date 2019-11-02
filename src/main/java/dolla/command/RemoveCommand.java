package dolla.command;

import dolla.DollaData;
import dolla.action.*;
import dolla.ui.RemoveUi;
import dolla.task.RecordList;

/**
 * RemoveCommand is a Command used to remove a Task from the TaskList.
 */
public class RemoveCommand extends Command {
    private String logNumStr;
    protected String mode;

    //@@author yetong1895
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
        int logNumInt;
        RecordList recordList = dollaData.getRecordListObj(mode);
        boolean isListEmpty = (recordList.size() == 0);

        if (isListEmpty) {
            return; // TODO: return error command
        }
        try {
            if (mode.equals(MODE_ENTRY)) {
                UndoStateList.addState(new EntryState(recordList.get()), mode);/////////////////////////////////
            } else if (mode.equals(MODE_DEBT)) {
                UndoStateList.addState(new DebtState(recordList.get()), mode);
            } else if (mode.equals(MODE_LIMIT)) {
                UndoStateList.addState(new LimitState(recordList.get()), mode);
            }
            Redo.clearRedoState(mode);
            logNumInt = stringToInt(logNumStr) - 1;
            RemoveUi.echoRemove(recordList.get().get(logNumInt).getRecordDetail());
            dollaData.removeFromRecordList(mode, logNumInt);
        } catch (IndexOutOfBoundsException e) {
            RemoveUi.printRemoveError(recordList.size());
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
