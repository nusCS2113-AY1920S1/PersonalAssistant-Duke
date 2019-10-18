package dolla.command;

import dolla.DollaData;
import dolla.Ui;

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
}
