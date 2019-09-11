package duke.command;

import duke.Duke;
import duke.core.DukeException;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Task;

/**
 * Represents a command class to add a task. The AddCommand class
 * extends from the Command class to represent user instruction
 * to add a new ToDo, Deadline or Event
 * task to the TaskList.
 */
public class AddCommand extends Command {
    /**
     * A new task to be added
     */
    private Task task;

    /**
     * Constructs a AddCommand object.
     *
     * @param task Specifies the task to be added.
     */
    public AddCommand(Task task) {
        super();
        this.task = task;
    }

    /**
     * Indicates whether Duke should exist
     *
     * @return A boolean. True if the command tells Duke to exit, false
     * otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param tasks   The task list where tasks are saved.
     * @param ui      The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            tasks.addTask(task);
            ui.taskAdded(task, tasks.getSize());
            storage.save(tasks.fullTaskList());
        } catch (DukeException e) {
            throw new DukeException("Fails to add task. " + e.getMessage());
        }
    }
}
