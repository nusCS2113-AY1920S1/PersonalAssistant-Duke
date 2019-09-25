package command;

import ui.UI;
import task.TaskList;
import storage.Storage;
import exception.DukeException;

/**
 * SearchCommand Class extends the abstract Command class.
 * Called when Searching for items in tasks.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class SearchCommand extends Command {
    private String command;
    private String arguments;

    public SearchCommand(String command, String input) {
        this.command = command;
        this.arguments = input;
    }

    /**
     * overwrites default execute to search for tasks.
     *
     * @param tasks   TasksList has tasks
     * @param ui      UI prints messages
     * @param storage Storage loads and saves files
     * @throws DukeException DukeException throws exception
     */
    public void execute(TaskList tasks, UI ui, Storage storage) throws DukeException {
        if (this.command.matches("find")) {
            tasks.find(this.arguments);
        }
        if (this.command.matches("schedule")) {
            tasks.view_schedule(this.arguments);
        }
    }
}
