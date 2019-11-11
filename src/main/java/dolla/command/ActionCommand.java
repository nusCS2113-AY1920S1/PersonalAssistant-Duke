package dolla.command;

import dolla.command.action.Redo;
import dolla.command.action.Undo;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.parser.ParserStringList;
import dolla.ui.ActionUi;

import java.util.ArrayList;

//@@author yetong1895
public class ActionCommand extends Command implements ParserStringList {
    private String mode;
    private String command;

    /**
     * This method will set the mode and command in this class.
     * @param mode the mode to be set in this class.
     * @param command the command to be set in this class.
     */
    public ActionCommand(String mode, String command) {
        this.mode = mode;
        this.command = command;
    }

    @Override
    public void execute(DollaData dollaData) {
        switch (command) {
        case COMMAND_UNDO :
            ArrayList<Record> recordList = Undo.processUndoState(mode);
            if (recordList != null) {
                Redo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
                ActionUi.printActionMessage(COMMAND_UNDO);
            }
            break;
        case COMMAND_REDO:
            recordList = Redo.processRedoState(mode);
            if (recordList != null) {
                Undo.addToStateList(mode, dollaData.getRecordList(mode));
                dollaData.setRecordList(recordList);
                ActionUi.printActionMessage(COMMAND_REDO);
            }
            break;
        default:
            break;
        }

    }

    @Override
    public String getCommandInfo() {
        return command + " in " + mode;
    }

}
