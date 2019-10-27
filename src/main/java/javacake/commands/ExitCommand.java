package javacake.commands;

import javacake.Logic;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.storage.Storage;

public class ExitCommand extends Command {
    public ExitCommand() {
        type = CmdType.EXIT;
    }

    /**
     * Executes exiting the program.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) {
        return "Bye. Hope to see you again soon!\n";
    }
}
