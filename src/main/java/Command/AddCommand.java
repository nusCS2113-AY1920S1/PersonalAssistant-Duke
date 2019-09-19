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
public class AddCommand extends Command {
    private String arguments;
    private String command;
    public AddCommand(String command, String arguments){
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * overwrites default execute to add tasks
     * @param tasks TasksList has tasks
     * @param ui UI prints messages
     * @param storage Storage loads and saves files
     * @throws DukeException DukeException throws exception
     */
    public void execute(TaskList tasks, UI ui, Storage storage) throws DukeException {
        if(this.command.matches("event")) {
            tasks.add(this.command, this.arguments);
            tasks.conflict_check();
        }
        else {
            tasks.add(this.command, this.arguments);
        }

    }

}
