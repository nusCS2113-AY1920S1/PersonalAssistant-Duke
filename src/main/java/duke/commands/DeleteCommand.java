package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.storage.Storage;
import duke.data.tasks.Task;

/**
 * Class representing a command to delete a task.
 */
public class DeleteCommand extends Command {
    private int index;
    private static final String MESSAGE_DELETE = "Alright! I've removed this task:\n  ";

    /**
     * Creates a new DeleteCommand with the given index.
     *
     * @param index The index of the task.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        try {
            Task task = storage.getTasks().remove(index);
            storage.write();
            return new CommandResult(MESSAGE_DELETE + task);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}
