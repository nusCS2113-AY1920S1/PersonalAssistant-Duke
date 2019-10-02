package duke.command;

import duke.core.DukeException;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Task;

/**
 * Represents a command to delete a task. The command.DeleteCommand class
 * extends from the Command class to represent user instruction
 * to delete an task from task list.
 */
public class DeleteCommand extends Command {
    /**
     * The index of the task to be deleted.
     */
    private int taskId;

    /**
     * Constructs a DeleteCommand object.
     *
     * @param taskId Specifies the index of the task to be deleted.
     */

    public DeleteCommand(int taskId) {
        super();
        this.taskId = taskId;
    }

    /**
     * Indicates whether Duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     *         otherwise.
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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            Task task = tasks.getTask(taskId);
            tasks.deleteTask(taskId);
            ui.taskRemoved(task, tasks.getSize());
            storage.save(tasks.fullTaskList());
        } catch (DukeException e) {
            throw new DukeException("Fails to delete task. " + e.getMessage());
        }
    }
}
