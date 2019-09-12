package duke.command;

import duke.core.DukeException;
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
    private int taskId;

    /**
     * Constructs a DoneCommand object.
     *
     * @param taskId Specifies the index of the task.
     */
    public DoneCommand(int taskId) {
        super();
        this.taskId = taskId;
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
            Task task = tasks.getTask(taskId);
            task.markAsDone();
            storage.save(tasks.fullTaskList());
            ui.markedAsDone(task);
        } catch (DukeException e) {
            throw new DukeException("Fails to mark task as done. " + e.getMessage());
        }
    }
}
