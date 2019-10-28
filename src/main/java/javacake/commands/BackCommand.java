package javacake.commands;

import javacake.Logic;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.storage.Storage;

public class BackCommand extends Command {

    public BackCommand() {
        type = CmdType.BACK;
    }

    /**
     * Execute going back to previous index.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws DukeException Error thrown when unable to close file reader
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {

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