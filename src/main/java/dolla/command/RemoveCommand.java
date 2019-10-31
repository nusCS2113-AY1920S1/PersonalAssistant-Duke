package dolla.command;

import dolla.DollaData;
import dolla.ui.RemoveUi;
import dolla.ui.Ui;
import dolla.action.Redo;
import dolla.action.Undo;
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
        RecordList recordList = dollaData.getRecordList(mode);
        boolean isListEmpty = (recordList.size() == 0);

        if (isListEmpty) {
            return; // TODO: return error command
        }
        try {
            if (logNumStr.contains("/")) { //input from undo
                resetUndoFlag();
                String[] parser = logNumStr.split("/", 2);
                logNumInt = stringToInt(parser[0]) - 1;
                Redo.addCommand(mode, recordList.get().get(logNumInt).getUserInput()); //add undo input to redo
            } else if (logNumStr.contains("|")) { //input form redo
                resetRedoFlag();
                String[] parser = logNumStr.split("//|", 2);
                logNumInt = stringToInt(parser[0]) - 1;
                Undo.addCommand(mode, recordList.get().get(logNumInt).getUserInput(), logNumInt); //add input to undo
            } else { //normal user input
                logNumInt = stringToInt(logNumStr) - 1;
                Undo.addCommand(mode, recordList.get().get(logNumInt).getUserInput(), logNumInt);
                Redo.clearRedo(mode);
            }
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
