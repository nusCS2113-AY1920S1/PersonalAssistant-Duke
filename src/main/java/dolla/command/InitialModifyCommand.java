package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.task.LogList;

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
        if (isIndexInList(dollaData.getLogList(currMode)) == true) {
            Ui.printInitialModifyMsg();
            dollaData.updateMode("modify " + currMode);
            dollaData.prepForModify(currMode, index);
        } else {
            Ui.printNoLogAssocError(index, currMode);
            return;
        }
    }

    /**
     * Returns true is the given index is within the LogList.
     * @param logList The LogList containing the Log to be modifed.
     * @return true if index is within the specified LogList.
     */
    private boolean isIndexInList(LogList logList) {
        if (index >= logList.size()) {
            return false;
        } else {
            return true;
        }
    }
}
