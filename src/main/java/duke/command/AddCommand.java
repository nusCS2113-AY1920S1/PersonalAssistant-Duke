package duke.command;

import duke.core.DukeExceptionThrow;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Task;

/**
 * Represents a command class to add a task. The AddCommand class
 * extends from the Command class to represent user instruction
 * to add a new ToDo, Deadline or Event
 * task to the duke.core.TaskList.
 */
public class AddCommand extends Command {
    /**
     * A new task to be added
     */
    private Task t;
    /**
     * Constructs a AddCommand object.
     * @param task Specifies the task to be added.
     */
    public AddCommand(Task task) {
        super();
        this.t = task;
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
