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
    private ArrayList<Record> recordList;

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
        case "undo":
            recordList = Undo.processUndoState(mode);
            if (recordList != null) {
                Redo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
                System.out.println("an undo entry have performed");
            }
            break;
        case "redo":
            recordList = Redo.processRedoState(mode);
            if (recordList != null) {
                Undo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
                System.out.println("an redo entry have performed");
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
