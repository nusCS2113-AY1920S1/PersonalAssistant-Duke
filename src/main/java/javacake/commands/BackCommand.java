package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class BackCommand extends Command {

    public BackCommand() {
        type = CmdType.BACK;
    }

    /**
     * Execute going back to previous index.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws CakeException Error thrown when unable to close file reader
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {

        logic.backToPreviousPath();
        logic.insertQueries();
        if (logic.containsDirectory()) {
            return (logic.displayDirectories());
        } else {
            logic.updateFilePath(logic.gotoFilePath(0));
            return (logic.readQuery());
        }
    }
}