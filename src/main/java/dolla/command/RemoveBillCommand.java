package dolla.command;

import dolla.model.DollaData;
import dolla.ui.DebtUi;

public class RemoveBillCommand extends Command {

    protected int billNum;

    public RemoveBillCommand(int billNum) {
        this.billNum = billNum;
    }

    @Override
    public void execute(DollaData dollaData) {
        dollaData.removeFromRecordList("bill", billNum - 1);
        DebtUi.printRemoveBillMessage();
    }

    @Override
    public String getCommandInfo() {
        return "bill " + billNum;
    }
}
