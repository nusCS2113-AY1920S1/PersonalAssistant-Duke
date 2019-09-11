package duke.command;

import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;

/**
 * Represents a command to list all existing tasks in the task list. The
 * <code>duke.command.ListCommand</code> class extends from the <code>duke.command.Command</code> class
 * for the user to view the entire task list from duke.Duke.
 */
public class ListCommand extends Command {
    /**
     * Constructs a <code>duke.command.ListCommand</code> object.
     */
    public ListCommand() {
        super();
    }
    /**
     * Indicates whether duke.Duke should exist
     * @return A boolean. True if the command tells duke.Duke to exit, false
     *          otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
    /**
     * run the command with the respect duke.core.TaskList, UI, and storage.
     * @param tasks The task list where tasks are saved.
     * @param ui The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.listTasks(tasks);
    }
}