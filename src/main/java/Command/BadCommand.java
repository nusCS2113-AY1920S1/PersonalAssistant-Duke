package Command;

import Storage.Storage;
import UI.UI;
import myTasks.TaskList;
import Exception.DukeException;

/**
 * AddCommand Class extends the abstract Command class
 * Called when items should be ADDED to tasks
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class BadCommand extends Command {
    BadCommand(String command, String arguments){
    }

    /**
     * overwrites default execute to throw errors
     * @param tasks TasksList has tasks
     * @param ui UI prints messages
     * @param storage Storage loads and saves files
     * @throws DukeException DukeException throws exception
     */
    public void execute(TaskList tasks, UI ui, Storage storage) throws DukeException {
        throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

}