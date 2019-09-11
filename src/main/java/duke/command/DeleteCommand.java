package duke.command;

import duke.core.DukeExceptionThrow;
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
    private int Id;
    /**
     * Constructs a DeleteCommand object.
     * @param taskId Specifies the index of the task to be deleted.
     */

    public DeleteCommand(int taskId) {
        super();
        this.Id = taskId;
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
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        try {
            Task t = tasks.getTask(Id);
            tasks.deleteTask(Id);
            ui.taskRemoved(t, tasks.getSize());
            storage.save(tasks.fullTaskList());
        }
        catch (IndexOutOfBoundsException e)
        {
            ui.showError(e.getMessage());
        }
    }
}
