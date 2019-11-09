package dolla.command;

import dolla.ModeStringList;
import dolla.model.Debt;
import dolla.model.DollaData;
import dolla.ui.DebtUi;
import dolla.ui.Ui;

public class SwitchModeCommand extends Command {
    private String newMode;

    public SwitchModeCommand(String newMode) {
        this.newMode = newMode;
    }

    @Override
    public void execute(DollaData dollaData) {
        dollaData.updateMode(newMode);
        Ui.printModeUpdated(dollaData.getMode());
        if (newMode.equals(ModeStringList.MODE_DEBT)) {
            DebtUi.printBillFeature();
        }
    }

    @Override
    public String getCommandInfo() {
        return newMode;
    }
}
