package command;

import task.TaskList;
import ui.Ui;
import storage.Storage;
import exception.DukeException;

/**
 * Represents a specified command as DoneCommand by extending the <code>Command</code> class.
 * Marks the task with given index as done.
 * Responses with the result.
 */
public class DoneCommand extends Command {
    private int index;

    /**
     * Constructs a <code>DoneCommand</code> object
     * given the index of the task to be marked as done.
     *
     * @param index The index of the task to be marked as done.
     */
    public DoneCommand(int index) {
        super("done");
        this.index = index;
    }

    /**
     * Lets the taskList of Duke mark the task with the given index as done and
     * updates content of storage file according to new taskList.
     * Responses the result to user by using ui of Duke.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException If the index given is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            tasks.done(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("☹ OOPS!!! The index should be in range.");
        }
        storage.update(tasks.toStorageStrings());

        ui.println("Nice! I've marked this task as done:");
        ui.println(tasks.getTaskInfo(index));
    }

}
