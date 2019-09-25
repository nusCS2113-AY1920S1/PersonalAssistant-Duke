package command;

import storage.Storage;
import ui.UI;
import task.TaskList;
import exception.DukeException;

/**
 * ExitCommand Class extends the abstract Command class.
 * Called when user wants to exit.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        this.exit = true;
    }

    /**
     * overwrites default execute to exit.
     *
     * @param tasks   TasksList has tasks
     * @param ui      UI prints messages
     * @param storage Storage loads and saves files
     * @throws DukeException DukeException throws exception
     */
    public void execute(TaskList tasks, UI ui, Storage storage) throws DukeException {
        boolean isClose = true;
        try {
            storage.store(tasks);
        } catch (DukeException e) {
            isClose = false;
            throw new DukeException("Exit Error:" + e.getLocalizedMessage());
        } finally {
            ui.hastaLaVista();
            if (isClose) {
                ui.closeSuccess();
            } else {
                ui.closeFailure();
            }
            ui.close();
        }
    }
}
