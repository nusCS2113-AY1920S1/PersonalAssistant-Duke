package javacake.commands;

import javacake.Profile;
import javacake.Storage;
import javacake.TaskList;
import javacake.Ui;
import javacake.tasks.Task;

public class ListCommand extends Command {
    public ListCommand() {
        type = CmdType.LIST;
    }

    /**
     * Execute the listing of current tasks on the Ui.
     * @param tasks TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Profile profile) {

    }
}
