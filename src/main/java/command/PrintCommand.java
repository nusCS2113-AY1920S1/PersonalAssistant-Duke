package command;

import ui.UI;
import task.TaskList;
import storage.Storage;
import exception.DukeException;

/**
 * PrintCommand Class extends the abstract Command class.
 * Called when printing items in tasks
 *
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class PrintCommand extends Command {
    protected PrintCommand() {
    }

    /**
     * overwrites default execute to print task in tasks.
     *
     * @param tasks   TasksList Object being used currently
     * @param ui      UI in charge of printing messages
     * @param storage Storage in charge of loading and saving files
     * @throws DukeException DukeException thrown when unable to execute
     */
    public void execute(TaskList tasks, UI ui, Storage storage) {
        tasks.print();
    }
}
