package javacake.commands;

import javacake.exceptions.CakeException;
import javacake.Logic;
import javacake.storage.StorageManager;
import javacake.storage.TaskList;
import javacake.storage.TaskState;
import javacake.ui.Ui;
import javacake.storage.Storage;

import java.io.File;

public class AddCommand extends Command {
    /**
     * Constructor for Adding of commands.
     * @param str Input string
     */
    public AddCommand(String str) {
        input = str;
        type = CmdType.DEADLINE;
    }

    /**
     * Execute addition of tasks.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws CakeException Shows error when deletion is not possible
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        String output = TaskList.runDeadline(storageManager.storage.getData(),
                input, TaskState.NOT_DONE);
        Storage.generateFolder(new File("data/tasks/"));
        storageManager.storage.write(storageManager.storage.getData());
        return output;
    }
}
