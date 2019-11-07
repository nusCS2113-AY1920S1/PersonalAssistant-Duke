package dolla.command;

import dolla.model.DollaData;
import dolla.model.RecordList;
import dolla.exception.DollaException;
import dolla.ui.DebtUi;
import dolla.ui.ListUi;

//@@author tatayu
/**
 * This class shows the bill list.
 */
public class ShowBillListCommand extends Command {

    private String mode;

    public ShowBillListCommand(String mode) {
        this.mode = mode;
    }

    @Override
    public void execute(DollaData dollaData) throws DollaException {
        RecordList recordList;
        recordList = dollaData.getBillRecordList();

        boolean listIsEmpty = (recordList.size() == 0);

        if (listIsEmpty) { // TODO: Place this in proper place
            ListUi.printEmptyListError(mode);
            return;
        } else {
            DebtUi.printBillList(recordList);
        }
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
