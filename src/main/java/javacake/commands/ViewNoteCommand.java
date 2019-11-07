package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ViewNoteCommand extends Command {

    public ViewNoteCommand(String inputCommand) {

    }
    
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        return null;
    }
}
