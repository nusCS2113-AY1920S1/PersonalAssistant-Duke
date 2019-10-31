package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ResetCommand extends Command {
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        return "Confirm reset and deletion of current Profile?\nType 'yes' to confirm\n"
                + "Type anything else to cancel\n";
    }
}
