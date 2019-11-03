package dolla.command;

import dolla.DollaData;
import dolla.action.Redo;
import dolla.action.Undo;
import dolla.task.Record;

import java.util.ArrayList;

//@@author yetong1895
public class AddActionCommand extends Command {
    private String mode;
    private String command;
    private static final String UNDO = "undo";
    private static final String REDO = "redo";

    /**
     * This method will set the mode and command in this class.
     * @param mode the mode to be set in this class.
     * @param command the command to be set in this class.
     */
    public AddActionCommand(String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    @Override
    public void execute(DollaData dollaData) throws Exception {
        switch (command) {
        case UNDO:
            ArrayList<Record> recordList = Undo.processUndoState(mode);
            if (recordList != null) {
                Redo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
            }
            break;
        case REDO:
            recordList = Redo.processRedoState(mode);
            if (recordList != null) {
                Undo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
            }
            break;
        default:
            break;
        }

    }

    @Override
    public String getCommandInfo() {
        return null;
    }

}
