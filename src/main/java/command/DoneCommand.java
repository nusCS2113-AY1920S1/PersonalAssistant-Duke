package command;

import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Represents a specified command as DoneCommand by extending the {@code Command} class.
 * Marks the task with given index as done.
 * Responses with the result.
 */
public class DoneCommand extends Command {

    /**
     * Constructs a {@code DoneCommand} object
     * given the index of the task to be marked as done.
     *
     * @param commandParams parameters used to invoke the command.
     */
    public DoneCommand(CommandParams commandParams) {
        super(commandParams);
    }

    /**
     * Lets the taskList of Duke mark the task with the given index as done and
     * updates content of storage file according to new taskList.
     * Responses the result to user by using ui of Duke.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException If the index given is out of range, invalid, or does not exist.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't know which task to set as done!");
        }
        String taskInfo;
        try {
            int index = Integer.parseInt(commandParams.getMainParam()) - 1; // 0-based
            tasks.done(index);
            taskInfo = tasks.getTaskInfo(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("☹ OOPS!!! The index should be in range.");
        } catch (NumberFormatException e) {
            throw new DukeException("☹ OOPS!!! The index be a number.");
        }
        storage.update(tasks.toStorageStrings());

        ui.println("Nice! I've marked this task as done:");
        ui.println(taskInfo);
    }

}
