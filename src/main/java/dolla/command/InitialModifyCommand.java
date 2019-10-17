package dolla.command;

import dolla.DollaData;
import dolla.Ui;
import dolla.task.LogList;

public class InitialModifyCommand extends Command {

    private int index;

    public InitialModifyCommand(String indexStr) {
        this.index = Integer.parseInt(indexStr)-1;
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

    private boolean isIndexInList(LogList logList) {
        if (index >= logList.size()) {
            return false;
        } else {
            return true;
        }
    }
}
