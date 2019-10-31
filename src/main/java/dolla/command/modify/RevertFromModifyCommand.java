package dolla.command.modify;

import dolla.DollaData;
import dolla.command.Command;
import dolla.ui.ModifyUi;

public class RevertFromModifyComand extends Command {

    @Override
    public void execute(DollaData dollaData) {
        dollaData.updateMode(dollaData.getPrevMode());
        ModifyUi.printCancelModifyMsg();
    }

    @Override
    public String getCommandInfo() {
        return null;
    }

}
