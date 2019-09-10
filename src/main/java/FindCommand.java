/**
 * Represents a command to find a certain task from Duke's task list.
 * The <code>ExitCommand</code> class extends from the <code>Command</code>
 * class for the user to find a specific task object from the storage.
 */
public class FindCommand extends Command {
    /**
     * Name of the task to be found.
     */
    private String taskName;
    /**
     * Constructs a <code>FindCommand</code> object.
     * @param name Specifies the name of the task.
     */
    public FindCommand(String name) {
        super();
        this.taskName = name;
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
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        ui.taskFound(tasks.fullTaskList(), taskName);
    }
}