package command;

import task.TaskList;
import ui.Ui;
import storage.Storage;
import exception.DukeException;

/**
 * Represents a specified command as DeleteCommand by extending the <code>Command</code> class.
 * Deletes the task with given index from the taskList of Duke.
 * Responses with the result.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Constructs a <code>DeleteCommand</code> object
     * given the index of the task to be deleted.
     *
     * @param index The index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        super("delete");
        this.index = index;
    }

    /**
     * Lets the taskList of Duke delete the task with the given index and
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
        String str;
        try {
            str = tasks.getTaskInfo(index);
            tasks.delete(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("☹ OOPS!!! The index should be in range.");
        }
        storage.update(tasks.toStorageStrings());
        ui.println("Noted. I've removed this task:");
        ui.println(str);
        ui.println("Now you have " + tasks.getSize() + " tasks in the list.");
    }
}
