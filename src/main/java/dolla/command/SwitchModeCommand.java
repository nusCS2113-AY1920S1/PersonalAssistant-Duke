package dolla.command;

import dolla.model.DollaData;
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
    }

    @Override
    public String getCommandInfo() {
        return newMode;
    }
}
