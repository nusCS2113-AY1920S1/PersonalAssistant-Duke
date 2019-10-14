package command;

import storage.Storage;
import task.Task;
import ui.UI;
import task.TaskList;
import exception.DukeException;
import list.DegreeList;

/**
 * AddCommand Class extends the abstract Command class.
 * Called when items should be ADDED to tasks.
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class AddCommand extends Command {
    private String arguments;
    private String command;
    private int listType = 0; //0 for task list, 1 for degree list

    /**
     * Creates AddCommand.
     * @param command command type to be used.
     * @param arguments to be added.
     */
    public AddCommand(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * overwrites default execute to add tasks.
     * @param tasks TasksList has tasks.
     * @param ui UI prints messages.
     * @param storage Storage loads and saves files.
     * @throws DukeException DukeException throws exception.
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists) throws DukeException {
        if (this.command.matches("event")) {
            this.listType = 0; //0 for task list
            memento.setState(tasks);
            tasks.add(this.command, this.arguments);
            tasks.conflict_check();
        }
        else if (this.command.matches("add")) {
            this.listType = 1;
            memento.setState(lists); //1 for degree list
            lists.add_custom(this.arguments);
        }
        else {
            this.listType = 0;
            memento.setState(tasks);
            tasks.add(this.command, this.arguments);
        }
    }

    public void unExecute(TaskList tasks, UI ui, Storage storage, DegreeList lists) throws DukeException {
        if (this.listType == 0) {
            tasks = memento.getTaskState();
        } else {
            lists = memento.getDegreeState();
        }
    }

}
