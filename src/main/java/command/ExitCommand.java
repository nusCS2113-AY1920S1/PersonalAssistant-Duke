package command;

import dictionary.Bank;
import storage.Storage;
import ui.Ui;

/**
 * Represents the command from user to exit application.
 * Inherits from Command class.
 */

public class ExitCommand extends Command {

    @Override
    public String execute(Ui ui, Bank bank, Storage storage) {
        System.exit(0);
        return null;
    }
}
