package duke.command;

import duke.core.DukeExceptionThrow;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Task;

/**
 * Represents a command to mark a task as done. The DoneCommand
 * class extends from the Command class to represent user
 * instruction to mark an existing task.
 */
public class DoneCommand extends Command {
    /**
     * The index of the task to be marked as done.
     */
    private int Id;
    /**
     * Constructs a DoneCommand object.
     * @param taskId Specifies the index of the task.
     */
    public DoneCommand(int taskId) {
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
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        try
        {
            Task t = tasks.getTask(Id);
            t.markAsDone();
            storage.save(tasks.fullTaskList());
            ui.markedAsDone(t);
        }
        catch (IndexOutOfBoundsException e)
        {
            ui.showError(e.getMessage());
        }
    }
}
