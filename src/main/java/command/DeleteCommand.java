package command;

import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a specified command as DeleteCommand by extending the {@code Command} class.
 * Deletes the task with given index from the taskList of Duke.
 * Responses with the result.
 */
public class DeleteCommand extends Command {

    /**
     * Constructs a {@code DeleteCommand} object
     * given the index of the task to be deleted.
     */
    public DeleteCommand() {
        super(null, null, null, null);
    }

    /**
     * Lets the taskList of Duke delete the task with the given index and
     * updates content of storage file according to new taskList.
     * Responses the result to user by using ui of Duke.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException If the index given is out of range, invalid, or does not exist.
     */
    public void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't know which task to delete!");
        }
        String taskInfo;
        try {
            int index = Integer.parseInt(commandParams.getMainParam()) - 1; // 0-based
            taskInfo = tasks.getTaskInfo(index);
            tasks.delete(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("☹ OOPS!!! The index should be in range.");
        } catch (NumberFormatException e) {
            throw new DukeException("☹ OOPS!!! The index be a number.");
        }
        storage.update(tasks.toStorageStrings());
        ui.println("Noted. I've removed this task:");
        ui.println(taskInfo);
        ui.println("Now you have " + tasks.getSize() + " tasks in the list.");
    }
}
