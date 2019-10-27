package javacake.commands;

import javacake.Logic;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ResetCommand extends Command {
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        return "Confirm reset and deletion of current Profile?\nType 'yes' to confirm\n"
                + "Type anything else to cancel\n";
    }
}
