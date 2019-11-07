package dolla.command.modify;

import dolla.command.Command;
import dolla.model.DollaData;
import dolla.ui.ListUi;
import dolla.ui.ModifyUi;
import dolla.model.RecordList;

//@@author omupenguin
/**
 * InitialModifyCommand is a command that runs first when the
 * user wants to execute a modify command.
 */
public class InitialModifyCommand extends Command {

    private int index;

    public InitialModifyCommand(String indexStr) {
        this.index = Integer.parseInt(indexStr) - 1;
    }

    @Override
    public void execute(DollaData dollaData) {
        String currMode = dollaData.getMode();
        if (isIndexInList(dollaData.getRecordListObj(currMode))) {
            ModifyUi.printInitialModifyMsg(currMode);
            dollaData.updateMode("modify " + currMode);
            dollaData.prepForModify(currMode, index);
        } else {
            ListUi.printNoRecordAssocError(index, currMode);
            return;
        }
    }

    /**
     * Returns true is the given index is within the recordList.
     * @param recordList The recordList containing the record to be modified.
     * @return true if index is within the specified recordList.
     */
    private boolean isIndexInList(RecordList recordList) {
        if (index >= recordList.size()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
