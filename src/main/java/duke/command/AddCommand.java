package duke.command;

import duke.core.DukeExceptionThrow;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Task;

/**
 * Represents a command class to add a task. The <code>duke.command.AddCommand</code> class
 * extends from the <code>duke.command.Command</code> class to represent user instruction
 * to add a new <code>ToDo</code>, <code>duke.task.Deadline</code> or <code>Event</code>
 * task to the <code>duke.core.TaskList</code>.
 */
public class AddCommand extends Command {
    /**
     * A new task to be added
     */
    private Task t;
    /**
     * Constructs a <code>duke.command.AddCommand</code> object.
     * @param tt Specifies the task to be added.
     */
    public AddCommand(Task tt) {
        super();
        this.t = tt;
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
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        tasks.addTask(t);
        ui.taskAdded(t, tasks.getSize());
        storage.save(tasks.fullTaskList());
    }
}
