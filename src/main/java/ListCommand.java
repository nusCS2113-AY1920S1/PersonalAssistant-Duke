/**
 * Represents a command to list all existing tasks in the task list. The
 * <code>ListCommand</code> class extends from the <code>Command</code> class
 * for the user to view the entire task list from Duke.
 */
class ListCommand extends Command {
    /**
     * Constructs a <code>ListCommand</code> object.
     */
    public ListCommand() {
        super();
    }
    /**
     * Indicates whether Duke should exist
     * @return A boolean. True if the command tells Duke to exit, false
     *          otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
    /**
     * run the command with the respect TaskList, UI, and storage.
     * @param tasks The task list where tasks are saved.
     * @param ui The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.listTasks(tasks);
    }
}