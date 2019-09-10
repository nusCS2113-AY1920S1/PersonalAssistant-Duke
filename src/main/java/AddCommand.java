/**
 * Represents a command class to add a task. The <code>AddCommand</code> class
 * extends from the <code>Command</code> class to represent user instruction
 * to add a new <code>ToDo</code>, <code>Deadline</code> or <code>Event</code>
 * task to the <code>TaskList</code>.
 */
public class AddCommand extends Command {
    /**
     * A new task to be added
     */
    private Task t;
    /**
     * Constructs a <code>AddCommand</code> object.
     * @param tt Specifies the task to be added.
     */
    public AddCommand(Task tt) {
        super();
        this.t = tt;
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
        tasks.addTask(t);
        ui.taskAdded(t, tasks.getSize());
        storage.save(tasks.fullTaskList());
    }
}
