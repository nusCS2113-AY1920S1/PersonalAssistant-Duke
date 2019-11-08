package dolla.command;

import dolla.model.DollaData;
import dolla.ui.Ui;

/**
 * Command to exit the program.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(DollaData dollaData) {
        Ui.printExitMsg();
        int exitStatus = 0;
        System.exit(exitStatus);
    }

    @Override
    public String getCommandInfo() {
        return "Exit";
    }
}
