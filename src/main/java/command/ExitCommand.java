package command;

import degree.DegreeManager;
import storage.Storage;
import ui.UI;
import task.TaskList;
import exception.DukeException;
import list.DegreeList;

import java.io.IOException;

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
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @param degreesManager
     * @throws DukeException DukeException throws exception
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreesManager) throws DukeException {
        boolean isClose = true;
        try {
            storage.store(tasks);
            storage.add_degrees(lists);
        } catch (DukeException e) {
            isClose = false;
            throw new DukeException("Exit Error: " + e.getLocalizedMessage());
        } finally {
            ui.hastaLaVista();
            if (isClose) {
                ui.closeSuccess();
            } else {
                ui.closeFailure();
            }
            //ui.close();
        }
    }
}
